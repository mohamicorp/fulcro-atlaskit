(ns fulcro-atlaskit.lozenge
  (:require
    ["@atlaskit/lozenge" :default Lozenge]
    [com.fulcrologic.fulcro.algorithms.react-interop :as react-interop]))

(def ui-lozenge (react-interop/react-factory Lozenge))
