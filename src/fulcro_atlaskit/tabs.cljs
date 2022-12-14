(ns fulcro-atlaskit.tabs
  (:require
    ["@atlaskit/tabs" :default Tabs :refer [Tab TabList TabPanel]]
    [com.fulcrologic.fulcro.algorithms.react-interop :as react-interop]))

(def ui-tabs (react-interop/react-factory Tabs))
(def ui-tab (react-interop/react-factory Tab))
(def ui-tab-list (react-interop/react-factory TabList))
(def ui-tab-panel (react-interop/react-factory TabPanel))
