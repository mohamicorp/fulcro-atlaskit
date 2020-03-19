(ns fulcro-atlaskit.icon.editor.link
  (:require ["@atlaskit/icon/glyph/editor/link" :default Link]
            [com.fulcrologic.fulcro.algorithms.react-interop :as interop]))

(def ui-icon (interop/react-factory Link))
