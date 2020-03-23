(ns fulcro-atlaskit.icon.arrow-right-circle
  (:require
    ["@atlaskit/icon/glyph/arrow-right-circle" :as arrow-right-circle]
    [com.fulcrologic.fulcro.algorithms.react-interop :as interop]))

(def ui-icon (interop/react-factory (.-default arrow-right-circle)))
