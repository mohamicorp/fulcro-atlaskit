(ns fulcro-atlaskit.side-navigation
  (:require
    ["@atlaskit/side-navigation" :as side-navigation]
    [com.fulcrologic.fulcro.algorithms.react-interop :as react-interop]))

(def ui-side-navigation (react-interop/react-factory side-navigation/SideNavigation))
(def ui-section (react-interop/react-factory side-navigation/Section))
(def ui-navigation-header (react-interop/react-factory side-navigation/NavigationHeader))
(def ui-header (react-interop/react-factory side-navigation/Header))
(def ui-heading-item (react-interop/react-factory side-navigation/HeadingItem))
(def ui-navigation-content (react-interop/react-factory side-navigation/NavigationContent))
(def ui-nestable-navigation-content (react-interop/react-factory side-navigation/NestableNavigationContent))
(def ui-button-item (react-interop/react-factory side-navigation/ButtonItem))
(def ui-link-item (react-interop/react-factory side-navigation/LinkItem))
(def ui-nesting-item (react-interop/react-factory side-navigation/NestingItem))
(def ui-footer (react-interop/react-factory side-navigation/Footer))
(def ui-navigation-footer (react-interop/react-factory side-navigation/NavigationFooter))
