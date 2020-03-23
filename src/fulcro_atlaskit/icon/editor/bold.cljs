(ns fulcro-atlaskit.icon.editor.bold
  (:require
    ["@atlaskit/icon/glyph/editor/bold" :as bold]
    [com.fulcrologic.fulcro.algorithms.react-interop :as interop]))

(def ui-icon (interop/react-factory (.-default bold)))
