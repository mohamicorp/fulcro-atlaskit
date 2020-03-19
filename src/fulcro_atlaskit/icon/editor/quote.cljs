(ns fulcro-atlaskit.icon.editor.quote
  (:require ["@atlaskit/icon/glyph/editor/quote" :default Quote]
            [com.fulcrologic.fulcro.algorithms.react-interop :as interop]))

(def ui-icon (interop/react-factory Quote))
