(ns fulcro-atlaskit.icon.check
  (:require
    ["@atlaskit/icon/glyph/check" :as check]
    ["@atlaskit/icon/glyph/check-circle-outline" :as check-outline]
    [com.fulcrologic.fulcro.algorithms.react-interop :as interop]))

(def ui-icon (interop/react-factory (.-default check)))
(def ui-check-outline (interop/react-factory (.-default check-outline)))
