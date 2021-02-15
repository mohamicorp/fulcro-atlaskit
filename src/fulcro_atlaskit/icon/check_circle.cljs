(ns fulcro-atlaskit.icon.check-circle
  (:require
    ["@atlaskit/icon/glyph/check-circle" :as check-circle]
    [com.fulcrologic.fulcro.algorithms.react-interop :as interop]))

(def ui-icon (interop/react-factory (.-default check-circle)))
