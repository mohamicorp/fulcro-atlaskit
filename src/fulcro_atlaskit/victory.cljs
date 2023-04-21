(ns fulcro-atlaskit.victory
  (:require
   ["victory" :as victory]
   [com.fulcrologic.fulcro.algorithms.react-interop :as interop]))

(def ui-victory-bar (interop/react-factory victory/VictoryBar))