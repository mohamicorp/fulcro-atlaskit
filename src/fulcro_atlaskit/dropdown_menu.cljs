(ns fulcro-atlaskit.dropdown-menu
  (:require
    ["@atlaskit/dropdown-menu" :default DropdownMenu :refer [DropdownItemGroup DropdownItem DropdownMenuStateless]]
    [com.fulcrologic.fulcro.algorithms.react-interop :as react-interop]))

(def ui-dropdown-menu (react-interop/react-factory DropdownMenu))
(def ui-dropdown-menu-stateless (react-interop/react-factory DropdownMenuStateless))
(def ui-dropdown-item-group (react-interop/react-factory DropdownItemGroup))
(def ui-dropdown-item (react-interop/react-factory DropdownItem))
