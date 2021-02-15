(ns fulcro-atlaskit.flag
  (:require
    ["@atlaskit/flag" :refer [FlagGroup AutoDismissFlag FlagsProvider useFlags] :as atlaskit-flag]
    [com.fulcrologic.fulcro.algorithms.react-interop :as interop]))

(def ui-flag (interop/react-factory (.-default atlaskit-flag)))
(def ui-flag-group (interop/react-factory FlagGroup))
(def ui-auto-dismiss-flag (interop/react-factory AutoDismissFlag))
(def ui-flags-provider (interop/react-factory FlagsProvider))
(def ui-use-flags useFlags)
