(ns fulcro-atlaskit.icon.trash
  (:require ["@atlaskit/icon/glyph/trash" :as trash]
            [com.fulcrologic.fulcro.algorithms.react-interop :as interop]))

(def ui-icon (interop/react-factory (.-default trash)))
