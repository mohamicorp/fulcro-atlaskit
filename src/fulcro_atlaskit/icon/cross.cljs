(ns fulcro-atlaskit.icon.cross
  (:require
    ["@atlaskit/icon/glyph/cross" :default Cross]
    [com.fulcrologic.fulcro.algorithms.react-interop :as interop]))

(def ui-icon (interop/react-factory Cross))
