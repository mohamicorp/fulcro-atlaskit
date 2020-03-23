(ns fulcro-atlaskit.icon.chevron-down
  (:require
    ["@atlaskit/icon/glyph/chevron-down" :default ChevronDown]
    [com.fulcrologic.fulcro.algorithms.react-interop :as react-interop]))

(def ui-icon (react-interop/react-factory ChevronDown))
