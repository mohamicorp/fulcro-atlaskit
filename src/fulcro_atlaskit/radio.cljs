(ns fulcro-atlaskit.radio
  (:require
    ["@atlaskit/radio" :refer [RadioGroup Radio]]
    [com.fulcrologic.fulcro.algorithms.react-interop :as interop]))

(def ui-radio-group (interop/react-factory RadioGroup))

(def ui-radio (interop/react-factory Radio))
