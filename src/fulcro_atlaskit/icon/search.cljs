(ns fulcro-atlaskit.icon.search
  (:require
    ["@atlaskit/icon/glyph/search" :default Search]
    [com.fulcrologic.fulcro.algorithms.react-interop :as interop]))

(def ui-search (interop/react-factory Search))
