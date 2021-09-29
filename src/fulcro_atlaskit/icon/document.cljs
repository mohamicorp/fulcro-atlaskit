(ns fulcro-atlaskit.icon.document
  (:require
    ["@atlaskit/icon/glyph/media-services/document" :default document]
    [com.fulcrologic.fulcro.algorithms.react-interop :as interop]))

(def ui-icon (interop/react-factory document))
