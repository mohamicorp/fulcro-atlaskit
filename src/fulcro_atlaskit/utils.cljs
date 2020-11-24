(ns fulcro-atlaskit.utils
  (:require
    [goog.object :as gobj]
    [com.fulcrologic.guardrails.core :refer [>defn =>]]
    [cljs.spec.alpha :as s]
    [com.fulcrologic.fulcro.data-fetch :as df]))

(>defn js-spread
  "This works like the javascript spread operator if you see examples like: {... props}"
  [m & args]
  [object? (s/* object?) => object?]
  (let [new-m (gobj/clone m)]
    (apply gobj/extend new-m args)
    new-m))

(defn vec-remove "Remove elem in coll by index." [coll pos] (vec (concat (subvec coll 0 pos) (subvec coll (inc pos)))))

(defn vec-add "Add elem in coll by index." [coll pos el] (vec (concat (subvec coll 0 pos) [el] (subvec coll pos))))

(defn vec-move "Move elem in coll by index"
  [coll pos1 pos2]
  (let [el (nth coll pos1)] (if (= pos1 pos2) coll (into [] (vec-add (vec-remove coll pos1) pos2 el)))))

(defn loading? ([props marker-kw] (loading? (get props [df/marker-table marker-kw])))
  ([marker] (or (df/ready? marker) (df/loading? marker))))

(def done? (complement loading?))

(def failed? df/failed?)

(defn prevent-default [e] (.preventDefault e))

(defn stop-propagation [e] (.stopPropagation e))
