(ns fulcro-atlaskit.textarea
  (:require
    ["@atlaskit/textarea" :default TextArea]
    [com.fulcrologic.fulcro.algorithms.react-interop :as interop]))

(def ui-textarea (interop/react-factory TextArea))
