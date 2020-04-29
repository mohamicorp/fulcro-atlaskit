(ns fulcro-atlaskit.icon.editor.close
  (:require
    ["@atlaskit/icon/glyph/editor/close" :default Close]
    [com.fulcrologic.fulcro.algorithms.react-interop :as interop]))

(def ui-icon (interop/react-factory Close))
