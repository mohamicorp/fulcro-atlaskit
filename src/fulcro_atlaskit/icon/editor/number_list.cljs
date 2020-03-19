(ns fulcro-atlaskit.icon.editor.number-list
  (:require ["@atlaskit/icon/glyph/editor/number-list" :default NumberList]
            [com.fulcrologic.fulcro.algorithms.react-interop :as interop]))

(def ui-icon (interop/react-factory NumberList))
