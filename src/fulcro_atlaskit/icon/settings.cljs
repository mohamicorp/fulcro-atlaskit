(ns fulcro-atlaskit.icon.settings
  (:require
    ["@atlaskit/icon/glyph/settings" :default Settings]
    [com.fulcrologic.fulcro.algorithms.react-interop :as interop]))

(def ui-icon (interop/react-factory Settings))
