(ns fulcro-atlaskit.onboarding
  (:require
    ["@atlaskit/onboarding"
     :refer
     [Spotlight SpotlightCard SpotlightManager SpotlightTarget SpotlightTransition SpotlightPulse]]
    [com.fulcrologic.fulcro.algorithms.react-interop :as interop]))

(def ui-spotlight (interop/react-factory Spotlight))
(def ui-spotlight-card (interop/react-factory SpotlightCard))
(def ui-spotlight-manager (interop/react-factory SpotlightManager))
(def ui-spotlight-target (interop/react-factory SpotlightTarget))
(def ui-spotlight-transition (interop/react-factory SpotlightTransition))
(def ui-spotlight-pulse (interop/react-factory SpotlightPulse))
