(ns fulcro-atlaskit.textfield
  (:require
    ["@atlaskit/textfield" :as atlaskit-textfield]
    [com.fulcrologic.fulcro.algorithms.react-interop :as interop]))

(def ui-textfield (interop/react-input-factory (.-default atlaskit-textfield)))
