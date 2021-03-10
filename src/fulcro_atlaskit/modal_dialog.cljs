(ns fulcro-atlaskit.modal-dialog
  (:require
    ["@atlaskit/modal-dialog" :default Modal :refer [ModalTransition]]
    [com.fulcrologic.fulcro.algorithms.react-interop :as react-interop]))

(def ui-modal (react-interop/react-factory Modal))
(def ui-modal-transition (react-interop/react-factory ModalTransition))
