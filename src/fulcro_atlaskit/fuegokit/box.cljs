(ns fulcro-atlaskit.fuegokit.box
  (:require
   ["@fuegokit/react/lib/components/Box" :default Box]
   [com.fulcrologic.fulcro.algorithms.react-interop :as react-interop]))

(def ui-box (react-interop/react-factory Box))