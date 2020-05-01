(ns fulcro-atlaskit.radio
  (:require
    ["@atlaskit/radio" :refer [RadioGroup]]
    [com.fulcrologic.fulcro.algorithms.react-interop :as interop]))

(def ui-radio-group (interop/react-factory RadioGroup))
