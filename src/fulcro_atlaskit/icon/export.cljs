(ns fulcro-atlaskit.icon.export
  (:require
    ["@atlaskit/icon/glyph/export" :as export]
    [com.fulcrologic.fulcro.algorithms.react-interop :as interop]))

(def ui-icon (interop/react-factory (.-default export)))
