(ns fulcro-atlaskit.inline-edit
  (:require
    ["@atlaskit/inline-edit" :default InlineEdit :refer [InlineEditableTextfield]]
    [com.fulcrologic.fulcro.algorithms.react-interop :as react-interop]))

(def ui-inline-edit (react-interop/react-factory InlineEditableTextfield))
(def ui-default-inline-edit (react-interop/react-factory InlineEdit))
