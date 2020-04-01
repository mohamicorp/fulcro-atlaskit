(ns fulcro-atlaskit.icon.checkbox
  (:require [com.fulcrologic.fulcro.algorithms.react-interop :as interop]
            ["@atlaskit/icon/glyph/checkbox" :default CheckBoxIcon]))

(def ui-icon (interop/react-factory CheckBoxIcon))
