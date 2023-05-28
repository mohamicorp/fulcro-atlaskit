(ns fulcro-atlaskit.modal-dialog
  (:require
    ["@atlaskit/modal-dialog" :default Modal :refer [ModalTransition ModalFooter ModalHeader ModalBody ModalTitle]]
    [com.fulcrologic.fulcro.algorithms.react-interop :as react-interop]))

(def ui-modal (react-interop/react-factory Modal))
(def ui-modal-footer (react-interop/react-factory ModalFooter))
(def ui-modal-header (react-interop/react-factory ModalHeader))
(def ui-modal-title (react-interop/react-factory ModalTitle))
(def ui-modal-body (react-interop/react-factory ModalBody))
(def ui-modal-transition (react-interop/react-factory ModalTransition))