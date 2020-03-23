(ns fulcro-atlaskit.inline-edit
  (:require
    ["@atlaskit/inline-edit" :refer [InlineEditableTextfield]]
    [com.fulcrologic.fulcro.algorithms.react-interop :as react-interop]))

(def ui-inline-edit (react-interop/react-factory InlineEditableTextfield))
