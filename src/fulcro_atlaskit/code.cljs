(ns fulcro-atlaskit.code
  (:require
    ["@atlaskit/code" :as atlaskit-code :refer [Code]]
    [com.fulcrologic.fulcro.algorithms.react-interop :as interop]))

(def ui-code (interop/react-factory Code))
