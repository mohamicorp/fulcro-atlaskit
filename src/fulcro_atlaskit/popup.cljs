(ns fulcro-atlaskit.popup
  (:require ["@atlaskit/popup" :default popup]
            [com.fulcrologic.fulcro.algorithms.react-interop :as react-interop]))

(def ui-popup (react-interop/react-factory popup))
