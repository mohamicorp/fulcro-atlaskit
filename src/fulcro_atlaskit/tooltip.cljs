(ns fulcro-atlaskit.tooltip
  (:require
    ["@atlaskit/tooltip" :as tooltip]
    [com.fulcrologic.fulcro.algorithms.react-interop :as interop]))

(def ui-tooltip (interop/react-factory (.-default tooltip)))
