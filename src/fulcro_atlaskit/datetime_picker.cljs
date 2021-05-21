(ns fulcro-atlaskit.datetime-picker
  (:require
    ["@atlaskit/datetime-picker" :refer [DatePicker DateTimePicker]]
    [com.fulcrologic.fulcro.algorithms.react-interop :as interop]))

(def ui-date-picker (interop/react-factory DatePicker))
(def ui-datetime-picker (interop/react-factory DateTimePicker))
