(ns fulcro-atlaskit.badge
  (:require
    ["@atlaskit/badge" :as badge :default Badge]
    [com.fulcrologic.fulcro.algorithms.react-interop :as react-interop]))

(def ui-badge (react-interop/react-factory Badge))
