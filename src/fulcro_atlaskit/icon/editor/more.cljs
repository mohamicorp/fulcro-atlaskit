(ns fulcro-atlaskit.icon.editor.more
  (:require ["@atlaskit/icon/glyph/editor/more" :default more]
            [com.fulcrologic.fulcro.algorithms.react-interop :as interop]))

(def ui-icon (interop/react-factory more))
