(ns fulcro-atlaskit.icon.no-image
  (:require
    ["@atlaskit/icon/glyph/media-services/no-image" :default NoImage]
    [com.fulcrologic.fulcro.algorithms.react-interop :as interop]))

(def ui-no-image (interop/react-factory NoImage))
