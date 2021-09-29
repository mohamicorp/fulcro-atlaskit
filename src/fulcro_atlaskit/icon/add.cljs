(ns fulcro-atlaskit.icon.add
  (:require
    ["@atlaskit/icon/glyph/add" :default Add]
    [com.fulcrologic.fulcro.algorithms.react-interop :as interop]))

(def ui-icon (interop/react-factory Add))
