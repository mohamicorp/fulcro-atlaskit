(ns fulcro-atlaskit.icon.drag-handler
  (:require ["@atlaskit/icon/glyph/drag-handler" :as drag-handler]
            [com.fulcrologic.fulcro.algorithms.react-interop :as interop]))

(def ui-icon (interop/react-factory (.-default drag-handler)))
