(ns fulcro-atlaskit.icon.error
  (:require
    ["@atlaskit/icon/glyph/error" :as error]
    [com.fulcrologic.fulcro.algorithms.react-interop :as interop]))

(def ui-icon (interop/react-factory (.-default error)))
