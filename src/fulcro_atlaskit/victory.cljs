(ns fulcro-atlaskit.victory
  (:require
   ["victory$default" :as victory]
   [com.fulcrologic.fulcro.algorithms.react-interop :as interop]))

(def ui-victory-bar (interop/react-factory victory/VictoryBar))