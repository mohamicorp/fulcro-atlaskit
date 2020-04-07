(ns fulcro-atlaskit.spinner
  (:require
    ["@atlaskit/spinner" :default Spinner]
    [com.fulcrologic.fulcro.algorithms.react-interop :as interop]))

(def ui-spinner (interop/react-factory Spinner))
