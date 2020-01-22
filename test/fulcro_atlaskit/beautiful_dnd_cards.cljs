(ns fulcro-atlaskit.beautiful-dnd-cards
  (:require [fulcro-atlaskit.beautiful-dnd :as dnd]
            [nubank.workspaces.core :as ws]
            [nubank.workspaces.model :as wsm]
            [goog.object :as gobj]
            [nubank.workspaces.card-types.fulcro3 :as ct.fulcro]
            [com.fulcrologic.fulcro-css.css :as css]
            [fulcro-atlaskit.utils :as fa-utils]
            [com.fulcrologic.fulcro.components :as comp :refer [defsc]]
            [com.fulcrologic.fulcro-css.localized-dom :as dom]))

(defsc Sale [this props]
  {:query [:sale/id
           :sale/name]
   :ident :sale/id
   :css [[:.sale {:border "3px solid red"
                  :padding "10px"
                  :height "50px"
                  :background "white"
                  :margin "10px"
                  :width "200px"}]]}
  (dnd/ui-draggable
   {:draggableId (str (:sale/id props)) :index (:sale/id props)}
   (fn [provided]
     (dom/div
      (fa-utils/js-spread
       (gobj/get provided "draggableProps")
       (gobj/get provided "dragHandleProps")
       #js {:ref (gobj/get provided "innerRef")
            :className (:sale (css/get-classnames Sale))})
      (:sale/name props)))))

(def ui-sale (comp/factory Sale {:keyfn :sale/id}))

(defn add-ref [provided]
  (doto (gobj/clone (gobj/get provided "droppableProps"))
    (gobj/set "ref" (gobj/get provided "innerRef"))))

(defsc FunnelStage [this {:sales-board.funnel-stage/keys [name id sales]}]
  {:query [:sales-board.funnel-stage/id
           :sales-board.funnel-stage/name
           :sales-board.funnel-stage/sales (comp/get-query Sale)]
   :ident :sales-board.funnel-stage/id
   :css [[:.wrapper {:position "relative"}]]}
  (dnd/ui-droppable {:droppableId (str "funnel-" id)}
                    (fn [provided, snapshot]
                      (comp/with-parent-context this
                                                (dom/div
                                                 (fa-utils/js-spread
                                                  (gobj/get provided "droppableProps")
                                                  #js {:ref (gobj/get provided "innerRef")})
                                                 (dom/h1 name)
                                                 (map ui-sale sales)
                                                 (gobj/get provided "placeholder"))))))

(def ui-funnel-stage (comp/factory FunnelStage {:keyfn :sales-board.funnel-stage/id}))

(defsc FulcroDemo
  [this {:root/keys [funnel-stages]}]
  {:initial-state (fn [_] {:root/funnel-stages [{:sales-board.funnel-stage/id 1
                                                 :sales-board.funnel-stage/name "Lead"
                                                 :sales-board.funnel-stage/sales (mapv
                                                                                  (fn [i]
                                                                                    {:sale/id i
                                                                                     :sale/name (str "sale-" i)})
                                                                                  (range 10))}
                                                {:sales-board.funnel-stage/id 2
                                                 :sales-board.funnel-stage/name "Won"
                                                 :sales-board.funnel-stage/sales (mapv
                                                                                  (fn [i]
                                                                                    {:sale/id i
                                                                                     :sale/name (str "sale-" i)})
                                                                                  (range 10 20))}]})
   :ident (fn [] [::id "singleton"])
   :css-include [Sale]
   :css [[:.wrapper {:display "flex"}]]
   :query [:root/funnel-stages (comp/get-query FunnelStage)]}
  (dnd/ui-drag-and-drop-context {:onDragEnd (fn [result]
                                              (js/console.log result)
                                              )}
                                (dom/h1 "Sales")
                                (dom/div :.wrapper
                                         (mapv ui-funnel-stage funnel-stages))))

(ws/defcard fulcro-demo-card
            {::wsm/align {:display "block"}}
            (ct.fulcro/fulcro-card
             {::ct.fulcro/root FulcroDemo}))
