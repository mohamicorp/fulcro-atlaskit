(ns fulcro-atlaskit.icon.editor.underline
  (:require ["@atlaskit/icon/glyph/editor/underline" :as underline]
            [com.fulcrologic.fulcro.algorithms.react-interop :as interop]))

(def ui-icon (interop/react-factory (.-default underline)))
