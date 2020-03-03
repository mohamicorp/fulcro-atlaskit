(ns fulcro-atlaskit.button
  (:require ["@atlaskit/button" :as atlaskit-button :refer [ButtonGroup]]
            [com.fulcrologic.fulcro.algorithms.react-interop :as interop]))

(def ui-atlaskit-button (interop/react-factory (.-default atlaskit-button)))
(def ui-atlaskit-button-group (interop/react-factory ButtonGroup))
