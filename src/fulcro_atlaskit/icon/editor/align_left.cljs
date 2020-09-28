(ns fulcro-atlaskit.icon.editor.align-left
  (:require
    ["@atlaskit/icon/glyph/editor/align-left" :default align-left]
    [com.fulcrologic.fulcro.algorithms.react-interop :as interop]))

(def ui-icon (interop/react-factory align-left))
