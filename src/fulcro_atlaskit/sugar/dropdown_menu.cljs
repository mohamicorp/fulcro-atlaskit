(ns fulcro-atlaskit.sugar.dropdown-menu
  (:require
    [com.fulcrologic.fulcro.components :as comp :refer [defsc]]
    [fulcro-atlaskit.dropdown-menu :as dropdown-menu]
    [fulcro-atlaskit.icon.more :as more]
    [cljs.spec.alpha :as s]
    [com.fulcrologic.fulcro-css.localized-dom :as dom]))

(s/def ::item-label string?)
(s/def ::item-tx vector?)
(s/def ::item-handler fn?)
(s/def ::item-disabled? boolean?)

(s/def ::item (s/keys :req [::item-label] :opt [::item-tx ::item-handler ::item-disabled?]))

(s/def ::items (s/coll-of ::item))

(defsc DropdownMenu [this {::keys [items]}]
  (dropdown-menu/ui-dropdown-menu
    {:position "bottom right"
     :triggerButtonProps
       #js
        {:iconBefore (more/ui-more)
         :appearance "subtle"}
     :triggerType "button"}
    (apply dropdown-menu/ui-dropdown-item-group
      {}
      (map
        (fn
          [{::keys [item-label item-tx item-handler item-disabled?]
            :or {item-disabled? false}}]
          (dropdown-menu/ui-dropdown-item
            {:isDisabled item-disabled?
             :onClick (or item-handler #(comp/transact! this item-tx))}
            (dom/div {:style #js {:minWidth "100px"}})
            item-label))
        items))))

(def ui-dropdown-menu (comp/factory DropdownMenu))
