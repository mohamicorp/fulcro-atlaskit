(ns fulcro-atlaskit.navigation
  (:require
    ["@atlaskit/atlassian-navigation" :as atlassian-navigation]
    [com.fulcrologic.fulcro.algorithms.react-interop :as react-interop]))

(def ui-navigation (react-interop/react-factory atlassian-navigation/AtlassianNavigation))
(def ui-create (react-interop/react-factory atlassian-navigation/Create))
(def ui-search (react-interop/react-factory atlassian-navigation/Search))
(def ui-settings (react-interop/react-factory atlassian-navigation/Settings))
(def ui-icon-button (react-interop/react-factory atlassian-navigation/IconButton))
(def ui-primary-button (react-interop/react-factory atlassian-navigation/PrimaryButton))
(def ui-app-switcher (react-interop/react-factory atlassian-navigation/AppSwitcher))
(def ui-dropdown-button (react-interop/react-factory atlassian-navigation/PrimaryDropdownButton))
(def generate-theme atlassian-navigation/generateTheme)
