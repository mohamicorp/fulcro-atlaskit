(ns fulcro-atlaskit.beautiful-dnd
  (:require
    ["react-beautiful-dnd" :refer [DragDropContext Droppable Draggable]]
    [com.fulcrologic.fulcro.algorithms.react-interop :as react-interop]))

(def ui-drag-and-drop-context (react-interop/react-factory DragDropContext))
(def ui-droppable (react-interop/react-factory Droppable))
(def ui-draggable (react-interop/react-factory Draggable))
