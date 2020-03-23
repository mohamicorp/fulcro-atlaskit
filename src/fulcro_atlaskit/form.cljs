(ns fulcro-atlaskit.form
  (:require
    ["@atlaskit/form" :refer [ErrorMessage HelperMessage Field FormFooter FormHeader] :as form]
    [com.fulcrologic.fulcro.algorithms.react-interop :as interop]))

(def ui-field (interop/react-factory Field))
(def ui-footer (interop/react-factory FormFooter))
(def ui-header (interop/react-factory FormHeader))
(def ui-form (interop/react-factory (.-default form)))
(def ui-helper-message (interop/react-factory HelperMessage))
(def ui-error-message (interop/react-factory ErrorMessage))
