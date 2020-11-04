(ns fulcro-atlaskit.icon.editor.align-center
  (:require
    ["@atlaskit/icon/glyph/editor/align-center" :default align-center]
    [com.fulcrologic.fulcro.algorithms.react-interop :as interop]))

(def ui-icon (interop/react-factory align-center))
