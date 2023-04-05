(ns fulcro-atlaskit.fuegokit
  (:require
   ["@fuegokit/react" :refer [Box  GlobalHeader]]
   [com.fulcrologic.fulcro.algorithms.react-interop :as react-interop]))

(def ui-box (react-interop/react-factory Box))
(def ui-global-header (react-interop/react-factory GlobalHeader))