(ns fulcro-atlaskit.inline-dialog
  (:require
    ["@atlaskit/inline-dialog" :default inline-dialog]
    [com.fulcrologic.fulcro.components :as comp :refer [defsc]]
    [com.fulcrologic.fulcro.algorithms.react-interop :as react-interop]
    [com.fulcrologic.fulcro-css.localized-dom :as dom]))

(def ui-atlaskit-inline-dialog (react-interop/react-factory inline-dialog))


(defsc InlineDialog [this {::keys [trigger]}]
  (let [open? (comp/get-state this :open?)]
    (ui-atlaskit-inline-dialog
      {:isOpen open?
       :onClose #(comp/set-state! this {:open? false})
       :content (comp/with-parent-context this (comp/children this))}
      (dom/div
        {:tabIndex 0
         :style
           {:outline "none"
            :cursor "pointer"}
         :onClick #(comp/update-state! this update :open? not)}
        (if (fn? trigger) (trigger open?) trigger)))))

(def ui-inline-dialog (comp/factory InlineDialog))
