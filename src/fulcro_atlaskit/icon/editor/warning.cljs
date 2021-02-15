(ns fulcro-atlaskit.icon.editor.warning
  (:require
    ["@atlaskit/icon/glyph/editor/warning" :as warning]
    [com.fulcrologic.fulcro.algorithms.react-interop :as interop]))

(def ui-icon (interop/react-factory (.-default warning)))
