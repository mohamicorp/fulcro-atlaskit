(ns fulcro-atlaskit.preload.debug
  (:require
    [taoensso.timbre :as log]
    [com.fulcrologic.fulcro.algorithms.timbre-support :refer [console-appender prefix-output-fn]]))

(log/set-level! :debug)

(log/merge-config!
  {:output-fn prefix-output-fn
   :appenders {:console (console-appender)}})

(log/warn "DEBUG MODE ACTIVE, do not include in PROD")
