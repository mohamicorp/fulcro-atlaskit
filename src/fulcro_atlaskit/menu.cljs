(ns fulcro-atlaskit.menu
  (:require ["@atlaskit/menu" :refer [MenuGroup Section HeadingItem ButtonItem]]
            [com.fulcrologic.fulcro.algorithms.react-interop :as react-interop]))

(def ui-menu-group (react-interop/react-factory MenuGroup))
(def ui-section (react-interop/react-factory Section))
(def ui-heading-item (react-interop/react-factory HeadingItem))
(def ui-button-item (react-interop/react-factory ButtonItem))
