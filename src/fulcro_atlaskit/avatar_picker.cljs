(ns fulcro-atlaskit.avatar-picker
  (:require
    ["@atlaskit/media-avatar-picker/dist/es5/image-navigator" :as image-navigator]
    [com.fulcrologic.fulcro.algorithms.react-interop :as interop]))

(def ui-avatar-picker (interop/react-factory image-navigator/ImageNavigator))
