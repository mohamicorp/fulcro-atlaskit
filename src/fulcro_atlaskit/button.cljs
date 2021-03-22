(ns fulcro-atlaskit.button
  (:require
    ["@atlaskit/button" :as atlaskit-button :refer [ButtonGroup]]
    [com.fulcrologic.fulcro.algorithms.react-interop :as interop]))

;; Keep this until we double checked that it is not used
(def ui-atlaskit-button (interop/react-factory (.-default atlaskit-button)))
(def ui-atlaskit-button-group (interop/react-factory ButtonGroup))

(def ui-button (interop/react-factory (.-default atlaskit-button)))
(def ui-loading-button (interop/react-factory (.-LoadingButton atlaskit-button)))
(def ui-button-group (interop/react-factory ButtonGroup))
