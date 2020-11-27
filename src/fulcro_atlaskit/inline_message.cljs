(ns fulcro-atlaskit.inline-message
  (:require
    ["@atlaskit/inline-message" :default InlineMessage]
    [com.fulcrologic.fulcro.algorithms.react-interop :as react-interop]))

(def ui-inline-message (react-interop/react-factory InlineMessage))
