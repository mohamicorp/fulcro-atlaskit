(ns fulcro-atlaskit.fuegokit
  (:require
   ["@fuegokit/react/lib-esm/index.js" :as fuegokit]
   [com.fulcrologic.fulcro.algorithms.react-interop :as react-interop]))

(def ui-fuegokit (react-interop/react-factory fuegokit))
;;(def ui-box (react-interop/react-factory Box))
;;(def ui-global-header (react-interop/react-factory GlobalHeader))