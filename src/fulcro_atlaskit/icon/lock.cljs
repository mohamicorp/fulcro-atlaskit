(ns fulcro-atlaskit.icon.lock
  (:require
    ["@atlaskit/icon/glyph/lock-filled" :default Lock]
    [com.fulcrologic.fulcro.algorithms.react-interop :as interop]))

(def ui-icon (interop/react-factory Lock))
