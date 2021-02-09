(ns fulcro-atlaskit.icon.editor.panel
  (:require
    ["@atlaskit/icon/glyph/editor/panel" :default Panel]
    [com.fulcrologic.fulcro.algorithms.react-interop :as interop]))

(def ui-icon (interop/react-factory Panel))
