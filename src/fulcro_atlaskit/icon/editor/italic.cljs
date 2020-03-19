(ns fulcro-atlaskit.icon.editor.italic
  (:require ["@atlaskit/icon/glyph/editor/italic" :as italic]
            [com.fulcrologic.fulcro.algorithms.react-interop :as interop]))

(def ui-icon (interop/react-factory (.-default italic)))
