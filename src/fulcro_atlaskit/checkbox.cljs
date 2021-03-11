(ns fulcro-atlaskit.checkbox
  (:require
    ["@atlaskit/checkbox" :refer [Checkbox]]
    [com.fulcrologic.fulcro.algorithms.react-interop :as interop]))

(def ui-checkbox (interop/react-factory Checkbox))
