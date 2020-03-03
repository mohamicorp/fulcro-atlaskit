(ns fulcro-atlaskit.icon.edit
  (:require ["@atlaskit/icon/glyph/edit" :as edit]
            [com.fulcrologic.fulcro.algorithms.react-interop :as interop]))

(def ui-icon (interop/react-factory (.-default edit)))
