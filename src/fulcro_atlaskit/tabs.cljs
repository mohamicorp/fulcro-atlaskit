(ns fulcro-atlaskit.tabs
  (:require
    ["@atlaskit/tabs" :as atlaskit-tabs]
    [com.fulcrologic.fulcro.algorithms.react-interop :as interop]))

(def ui-tabs (interop/react-factory (.-default atlaskit-tabs)))
