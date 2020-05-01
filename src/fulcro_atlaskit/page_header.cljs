(ns fulcro-atlaskit.page-header
  (:require
    ["@atlaskit/page-header" :default PageHeader]
    [com.fulcrologic.fulcro.algorithms.react-interop :as react-interop]))

(def ui-page-header (react-interop/react-factory PageHeader))
