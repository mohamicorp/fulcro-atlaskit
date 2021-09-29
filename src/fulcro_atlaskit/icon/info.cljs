(ns fulcro-atlaskit.icon.info
  (:require
    ["@atlaskit/icon/glyph/info" :as info]
    [com.fulcrologic.fulcro.algorithms.react-interop :as interop]))

(def ui-icon (interop/react-factory (.-default info)))
