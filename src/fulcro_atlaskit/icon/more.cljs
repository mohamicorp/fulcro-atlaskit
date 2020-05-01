(ns fulcro-atlaskit.icon.more
  (:require
    ["@atlaskit/icon/glyph/more" :default More]
    [com.fulcrologic.fulcro.algorithms.react-interop :as interop]))

(def ui-more (interop/react-factory More))
