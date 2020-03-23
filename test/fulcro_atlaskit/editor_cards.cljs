(ns fulcro-atlaskit.editor-cards
  (:require
    [cljs-bean.core :refer [bean ->clj ->js]]
    [nubank.workspaces.core :as ws]
    [nubank.workspaces.model :as wsm]
    [nubank.workspaces.card-types.fulcro3 :as ct.fulcro]
    [fulcro-atlaskit.editor :as editor]
    [fulcro-atlaskit.button :as button]
    [com.fulcrologic.fulcro.mutations :refer [defmutation]]
    [com.fulcrologic.fulcro.components :as comp :refer [defsc]]
    [com.fulcrologic.fulcro-css.localized-dom :as dom]
    [com.fulcrologic.fulcro.algorithms.react-interop :as react-interop]
    ["slate" :refer [createEditor]]
    ["slate-react" :refer [Slate Editable withReact]]))

(def ui-slate (react-interop/react-factory Slate))
(def ui-editable (react-interop/react-factory Editable))

(defsc FulcroDemo [this props]
  {:initial-state (fn [_] {:id "autocomplete"})
   :initLocalState
     (fn []
       {:editor (editor/create-editor)
        :value
          (->js
            [{:type "paragraph"
              :children [{:text "A line of text in a paragraph"}]}])})
   :ident :id
   :query [:id]
   :css [[:.wrapper {:width "100%"}]]
   :css-include [editor/AtlasEditor]}
  (dom/div
    :.wrapper
    (editor/ui-editor
      {:editor (comp/get-state this :editor)
       :value (comp/get-state this :value)
       :on-change #(comp/update-state! this assoc :value %)})
    (button/ui-button
      {:onClick (fn [e] (.preventDefault e) (js/console.log (js->clj (comp/get-state this :value))))}
      "Print format in console")))

(ws/defcard
  kitchensink-demo
  {::wsm/align
     {:display "block"
      :overflow "scroll"
      :width "100%"}}
  (ct.fulcro/fulcro-card {::ct.fulcro/root FulcroDemo}))
