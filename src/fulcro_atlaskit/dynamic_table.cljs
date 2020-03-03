(ns fulcro-atlaskit.dynamic-table
  (:require ["@atlaskit/dynamic-table" :as dynamic-table]
            [com.fulcrologic.fulcro.algorithms.react-interop :as interop]))

(def stateless (interop/react-factory dynamic-table/default))
