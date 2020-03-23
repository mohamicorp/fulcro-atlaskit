(ns fulcro-atlaskit.select-cards
  (:require
    [cljs-bean.core :refer [bean ->clj]]
    [nubank.workspaces.core :as ws]
    [nubank.workspaces.model :as wsm]
    [nubank.workspaces.card-types.fulcro3 :as ct.fulcro]
    [com.fulcrologic.fulcro.mutations :as m :refer [defmutation]]
    [com.fulcrologic.fulcro.components :as comp :refer [defsc]]
    [fulcro-atlaskit.sugar.select :as select-sugar]
    [com.fulcrologic.fulcro-css.localized-dom :as dom]
    [com.fulcrologic.fulcro.routing.dynamic-routing]
    [com.fulcrologic.fulcro.algorithms.tx-processing :as txn]
    [com.fulcrologic.fulcro.ui-state-machines :as uism]
    [com.wsscode.pathom.sugar :as ps]
    [com.wsscode.pathom.connect :as pc]
    [com.fulcrologic.fulcro.rendering.keyframe-render2 :as keyframe-render2]
    [com.fulcrologic.fulcro.algorithms.tx-processing :as txn]
    [taoensso.timbre :as log]
    [edn-query-language.core :as eql]
    [cljs.core.async :as async]
    [fulcro-atlaskit.button :as button]))

(defn not-aborted? [{:keys [active-requests]} abort-id] (contains? @active-requests abort-id))

(defn mock-http-server
  "Create a remote that mocks a Fulcro remote server.

  :parser - A function `(fn [eql-query] async-channel)` that returns a core async channel with the result for the
  given eql-query."
  [{:keys [parser]
    :as options}]
  (merge
    options
    {:active-requests (atom #{})
     :transmit!
       (fn transmit!
         [{:keys [active-requests]
           :as this}
          {:keys [::txn/ast ::txn/result-handler ::txn/update-handler]
           :as send-node}]
         (let [edn (eql/ast->query ast)
               ok-handler (fn [result]
                            (try
                              (result-handler result)
                              (catch :default e (log/error e "Result handler failed with an exception."))))
               error-handler (fn [error-result]
                               (try
                                 (result-handler
                                   (merge
                                     {:status-code 500}
                                     (select-keys error-result #{:transaction :status-code :body :status-text})))
                                 (catch :default e (log/error e "Error handler failed with an exception."))))
               abort-id (or
                          (->
                            send-node
                            ::txn/options
                            ::txn/abort-id)
                          (->
                            send-node
                            ::txn/options
                            :abort-id))]
           (when abort-id (swap! active-requests conj abort-id))
           (try
             (async/go
               (let [result (async/<! (parser edn))]
                 (if (not-aborted? this abort-id)
                   (if (rand-nth '(true false))
                     (ok-handler
                       {:transaction edn
                        :status-code 200
                        :body result})
                     (error-handler
                       {:transaction edn
                        :status-code 500}))
                   (do
                     (log/warn "Request canceled" {:abort-id abort-id})
                     (ok-handler
                       {:status-text "Cancelled"
                        ::txn/aborted? true})))))
             (catch :default e
               (error-handler
                 {:transaction edn
                  :status-code 500})))))
     :abort!
       (fn abort!
         [{:keys [active-requests]
           :as this}
          id]
         (log/debug "Aborting" id)
         (when (not-aborted? this id) (swap! active-requests disj id)))}))

(pc/defresolver person-autocomplete [env input]
  {::pc/output [{:person/autocomplete [:person/id :person/name]}]}
  (let [{:keys [filter-value]} (get-in env [:ast :params])]
    (async/go
      (async/<! (async/timeout 700))
      {:person/autocomplete
         (into
           [{:person/id "foo"
             :person/name "bar"}]
           (mapv
             (fn [i]
               {:person/id (str filter-value "__" i)
                :person/name (str filter-value " " i)})
             (range 20)))})))

(def parser (ps/connect-async-parser [person-autocomplete]))

(def mock-http (mock-http-server {:parser #(parser {} %)}))

(defsc Person [this props]
  {:query [:person/id :person/name]
   :ident :person/id})

(defn person->option
  [{:person/keys [id name]
    :as person}]
  #js
   {:label name
    :value id
    :ident (comp/get-ident Person person)})

(defmutation set-selected [{:keys [id ident]}]
  (action [{:keys [state]}] (swap! state assoc-in [:person-selector/id id :ui/selected] ident)))

(defmutation set-multi [{:keys [id multi?]}]
  (action [{:keys [state]}] (swap! state update-in [:person-selector/id id] assoc :ui/multi? multi? :ui/selected nil)))

(defsc PersonSelector
  [this
   {:person-selector/keys [id]
    :ui/keys [selected multi?]
    :as props}]
  {::select-sugar/option-class Person
   ::select-sugar/server-property :person/autocomplete
   ::select-sugar/filtering :remote
   :initial-state
     (fn [_]
       {:ui/select (comp/get-initial-state select-sugar/Select {::select-sugar/id "select"})
        :ui/options []
        :ui/selected nil
        :person-selector/id :select-person
        :ui/multi? false})
   :ident :person-selector/id
   :query
     [{:ui/select (comp/get-query select-sugar/Select)}
      {:ui/options (comp/get-query Person)}
      {:ui/selected (comp/get-query Person)}
      :ui/multi?
      :person-selector/id]
   :css-include [select-sugar/Select]
   :componentDidMount
     (fn [this]
       (let [{:person-selector/keys [id]} (comp/props this)]
         (select-sugar/start-select!
           this
           {::select-sugar/results-actor [:person-selector/id id]
            ::select-sugar/id "select"})))
   :componentWillUnmount
     (fn [this] (let [{:person-selector/keys [id]} (comp/props this)] (select-sugar/stop-select! this id)))}
  (log/debug "SELECTED" (:ui/selected props))
  (dom/div
    (button/ui-button
      {:onClick
         (fn []
           (comp/transact!
             this
             [(set-multi
                {:multi? (not multi?)
                 :id id})]))}
      (if multi? "Toggle to single select" "Toggle to multi select"))
    (select-sugar/ui-select
      (comp/computed
        (:ui/select props)
        {::select-sugar/uism-id "select"
         ::select-sugar/on-select-mutation (set-selected {:id id})
         ::select-sugar/react-select-props {:isMulti multi?}
         ::select-sugar/selected
           (if multi?
             (mapv person->option selected)
             (some->
               selected
               person->option))
         ::select-sugar/options (mapv person->option (:ui/options props))}))))

(def ui-person-selector (comp/factory PersonSelector))

(defsc FulcroDemo [this props]
  {:initial-state
     (fn [_]
       {:id "autocomplete"
        :ui/person-selector (comp/get-initial-state PersonSelector)})
   :ident :id
   :query [:id {:selected-person (comp/get-query Person)} {:ui/person-selector (comp/get-query PersonSelector)}]
   :css [[:.wrapper {:width "100%"}]]}
  (dom/div :.wrapper (ui-person-selector (:ui/person-selector props))))

(ws/defcard
  kitchensink-demo
  {::wsm/align
     {:display "block"
      :width "100%"}}
  (ct.fulcro/fulcro-card
    {::ct.fulcro/root FulcroDemo
     ::ct.fulcro/app
       {:remotes {:remote mock-http}
        :optimized-render! keyframe-render2/render!}}))
