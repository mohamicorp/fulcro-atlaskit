(ns fulcro-atlaskit.utils
  (:require [goog.object :as gobj]))

(defn js-spread
  "This works like the javascript spread operator if you see examples like: {... props}"
  [m & args]
  (let [new-m (gobj/clone m)]
    (apply gobj/extend new-m args)
    new-m))
