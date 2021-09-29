(ns fulcro-atlaskit.icon.warning
  (:require
    ["@atlaskit/icon/glyph/warning" :as warning]
    [com.fulcrologic.fulcro.algorithms.react-interop :as interop]))

(def ui-icon (interop/react-factory (.-default warning)))
