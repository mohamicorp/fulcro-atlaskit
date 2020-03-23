(ns fulcro-atlaskit.development-preload
  (:require
    [taoensso.timbre :as log]
    [com.fulcrologic.fulcro.algorithms.timbre-support :refer [console-appender prefix-output-fn]]))

(log/set-level! :debug)
(log/merge-config!
  {:output-fn prefix-output-fn
   :appenders {:console (console-appender)}})
