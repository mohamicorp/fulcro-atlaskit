(ns fulcro-atlaskit.icon.open
  (:require
    ["@atlaskit/icon/glyph/open" :as open]
    [com.fulcrologic.fulcro.algorithms.react-interop :as interop]))

(def ui-icon (interop/react-factory (.-default open)))
