(ns fulcro-atlaskit.select
  (:require ["@atlaskit/select" :as atlaskit-select]
            [com.fulcrologic.fulcro.algorithms.react-interop :as interop]))

(def ui-select (interop/react-factory (.-default atlaskit-select)))
