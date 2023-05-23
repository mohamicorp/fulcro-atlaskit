(ns app.fuegokit
  (:require
   ["@fuegokit/react" :as fuegokit]
   ["react" :refer [Fragment]]
   [com.fulcrologic.fulcro.algorithms.react-interop :as react-interop]))

(def ui-box (react-interop/react-factory fuegokit/Box))
(def ui-global-header (react-interop/react-factory fuegokit/GlobalHeader))
(def ui-global-header-nav (react-interop/react-factory (.-Nav fuegokit/GlobalHeader)))
(def ui-global-header-navtitle (react-interop/react-factory (.-NavTitle fuegokit/GlobalHeader)))
(def ui-maxwidth-wrapper (react-interop/react-factory fuegokit/MaxWidthWrapper))
(def ui-spacer (react-interop/react-factory fuegokit/Spacer))
(def ui-getstarted-stack (react-interop/react-factory fuegokit/GettingStartedStack))
(def ui-pagehero (react-interop/react-factory fuegokit/PageHero))

(def ui-fragment (react-interop/react-factory Fragment))

(def ui-footer-card (react-interop/react-factory fuegokit/FooterCard))
(def ui-footer-grid (react-interop/react-factory fuegokit/FooterCardGrid))
(def ui-footer-title (react-interop/react-factory fuegokit/FooterTitle))
(def ui-footer-section (react-interop/react-factory fuegokit/FooterSection))
(def ui-footer-nav (react-interop/react-factory fuegokit/FooterNav))
(def ui-footer-nav-wrap (react-interop/react-factory (.-Wrapper fuegokit/FooterNav)))
(def ui-footer-nav-logo (react-interop/react-factory (.-Logo fuegokit/FooterNav)))
(def ui-footer-nav-item (react-interop/react-factory (.-Item fuegokit/FooterNav)))
(def ui-footer-nav-copyright (react-interop/react-factory (.-Copyright fuegokit/FooterNav)))