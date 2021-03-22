(ns fulcro-atlaskit.icon.custom-icon
  (:require
    ["@atlaskit/icon" :default Icon]
    [com.fulcrologic.fulcro.algorithms.react-interop :as interop]))

(def ui-icon (interop/react-factory Icon))
