(ns fulcro-atlaskit.icon.editor.bullet-list
  (:require ["@atlaskit/icon/glyph/editor/bullet-list" :default BulletList]
            [com.fulcrologic.fulcro.algorithms.react-interop :as interop]))

(def ui-icon (interop/react-factory BulletList))
