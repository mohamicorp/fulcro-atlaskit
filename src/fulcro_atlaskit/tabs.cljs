(ns fulcro-atlaskit.tabs
  (:require
    ["@atlaskit/tabs" :as atlaskit-tabs :refer [Tab TabList TabPanel]]
    [com.fulcrologic.fulcro.algorithms.react-interop :as interop]))

(def ui-tabs (interop/react-factory (.-default atlaskit-tabs)))
(def ui-tab (interop/react-factory Tab))
(def ui-tab-list (interop/react-factory TabList))
(def ui-tab-panel (interop/react-factory TabPanel))
