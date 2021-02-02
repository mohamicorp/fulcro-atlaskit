(ns fulcro-atlaskit.toggle
  (:require
    ["@atlaskit/toggle" :as atlaskit-toggle]
    [com.fulcrologic.fulcro.algorithms.react-interop :as interop]))

(def ui-toggle (interop/react-factory (.-default atlaskit-toggle)))
