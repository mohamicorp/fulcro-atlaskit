(ns fulcro-atlaskit.inline-edit
  (:require ["@atlaskit/inline-edit" :refer [InlineEditableTextfield]]
            [com.fulcrologic.fulcro-css.localized-dom :as dom]
            [goog.object :as gobj]
            [fulcro-atlaskit.utils :as fa-utils]
            [com.fulcrologic.fulcro.components :as comp :refer [defsc]]
            [com.fulcrologic.fulcro.algorithms.react-interop :as react-interop]))

(def ui-react-inline-edit (react-interop/react-factory InlineEditableTextfield))

(defsc InlineEdit [this {:keys [defaultValue onConfirm isCompact label validate]}]
  (ui-react-inline-edit {:defaultValue defaultValue
                          :onConfirm onConfirm
                          :isCompact isCompact
                         :label label
                          :validate validate}))

(def ui-inline-edit (comp/factory InlineEdit))

