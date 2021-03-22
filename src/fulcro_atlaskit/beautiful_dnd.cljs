(ns fulcro-atlaskit.beautiful-dnd
  (:require
    ["react-beautiful-dnd" :refer [DragDropContext Droppable Draggable]]
    [com.fulcrologic.fulcro.algorithms.react-interop :as react-interop]
    [fulcro-atlaskit.colors :as colors]))

(def ui-drag-and-drop-context (react-interop/react-factory DragDropContext))
(def ui-droppable (react-interop/react-factory Droppable))
(def ui-draggable (react-interop/react-factory Draggable))

(def dropzone-css
  {:box-sizing "border-box"
   :border-style "dashed"
   :border-color colors/N0
   :border-width "2px"
   :border-radius "4px"
   :transition "background 200ms, borderColor 200ms"
   :transition-timing-function "cubic-bezier(0.215,0.61,0.355,1)"})

(def dropzone-active-css
  {:border-color colors/B100
   :background colors/B50})

(def dropzone-hover-css
  {:border-color colors/B300
   :background colors/B75})
