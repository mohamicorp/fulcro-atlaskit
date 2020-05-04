(ns fulcro-atlaskit.empty-state
  (:require
    ["@atlaskit/empty-state" :default EmptyState]
    [com.fulcrologic.fulcro.algorithms.react-interop :as react-interop]))

(def ui-empty-state (react-interop/react-factory EmptyState))
