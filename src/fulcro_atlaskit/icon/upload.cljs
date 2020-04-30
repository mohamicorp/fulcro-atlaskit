(ns fulcro-atlaskit.icon.upload
  (:require
    ["@atlaskit/icon/glyph/upload" :as upload]
    [com.fulcrologic.fulcro.algorithms.react-interop :as interop]))

(def ui-icon (interop/react-factory (.-default upload)))
