(ns fulcro-atlaskit.section-message
  (:require
    ["@atlaskit/section-message" :default section-message]
    [com.fulcrologic.fulcro.algorithms.react-interop :as react-interop]))

(def ui-section-message (react-interop/react-factory section-message))
