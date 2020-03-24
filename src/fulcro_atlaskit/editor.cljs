(ns fulcro-atlaskit.editor
  (:require
    [com.fulcrologic.fulcro.algorithms.react-interop :as react-interop]
    [com.fulcrologic.fulcro.components :as comp :refer [defsc]]
    [com.fulcrologic.guardrails.core :refer [>def >defn]]
    [fulcro.i18n :refer [tr]]
    [com.fulcrologic.fulcro-css.localized-dom :as dom]
    [fulcro-atlaskit.button :as button]
    [fulcro-atlaskit.utils :as utils]
    [fulcro-atlaskit.colors :as colors]
    [fulcro-atlaskit.popup :as popup]
    [fulcro-atlaskit.icon.editor.bold :as bold]
    [fulcro-atlaskit.icon.editor.italic :as italic]
    [fulcro-atlaskit.icon.editor.more :as more]
    [fulcro-atlaskit.icon.editor.quote :as quote]
    [fulcro-atlaskit.icon.editor.number-list :as number-list]
    [fulcro-atlaskit.icon.editor.bullet-list :as bullet-list]
    [fulcro-atlaskit.icon.chevron-down :as chevron-down]
    [fulcro-atlaskit.tooltip :as tooltip]
    [fulcro-atlaskit.menu :as menu]
    [garden.color :as garden-color]
    [cljs-bean.core :refer [bean ->js]]
    [goog.object :as gobj]
    ["slate" :refer [Editor createEditor Transforms]]
    ["slate-history" :refer [withHistory]]
    ["is-hotkey" :default isHotkey]
    ["slate-react" :refer [Slate Editable withReact useSlate]]
    [clojure.string :as str]
    [taoensso.timbre :as log]
    [goog.userAgent :as guser-agent]
    [fulcro.client.primitives :as prim]))

(def mac? guser-agent/MAC)

(def ui-slate (react-interop/react-factory Slate))
(def ui-editable (react-interop/react-factory Editable))

(>def ::type #{::mark ::block})
(>def ::shortcut vector?)
(>def ::format string?)

(def format-options
  {:bold
     {::shortcut ["mod" "b"]
      ::format "bold"
      ::type ::mark}
   :italic
     {::shortcut ["mod" "i"]
      ::format "italic"
      ::type ::mark}
   :underline
     {::shortcut ["mod" "u"]
      ::format "underline"
      ::type ::mark}
   :strikethrough
     {::shortcut ["mod" "shift" "s"]
      ::format "strikethrough"
      ::type ::mark}
   :code
     {::shortcut ["mod" "shift" "m"]
      ::format "code"
      ::type ::mark}
   :superscript
     {::type ::mark
      ::format "superscript"}
   :subscript
     {::type ::mark
      ::format "subscript"}
   :paragraph
     {::type ::block
      ::format "paragraph"}
   :heading-1
     {::type ::block
      ::format "heading-1"
      ::shortcut ["mod" "alt" "1"]}
   :heading-2
     {::type ::block
      ::format "heading-2"
      ::shortcut ["mod" "alt" "2"]}
   :heading-3
     {::type ::block
      ::format "heading-3"
      ::shortcut ["mod" "alt" "3"]}
   :heading-4
     {::type ::block
      ::format "heading-4"
      ::shortcut ["mod" "alt" "4"]}
   :heading-5
     {::type ::block
      ::format "heading-5"
      ::shortcut ["mod" "alt" "5"]}
   :heading-6
     {::type ::block
      ::format "heading-6"
      ::shortcut ["mod" "alt" "6"]}
   :numbered-list
     {::type ::block
      ::format "numbered-list"
      ::shortcut ["mod" "alt" "7"]}
   :bullet-list
     {::type ::block
      ::format "bullet-list"
      ::shortcut ["mod" "alt" "8"]}
   :list-item
     {::type ::block
      ::format "list-item"}
   :blockquote
     {::type ::block
      ::format "blockquote"}})

(def list-types #{(get-in format-options [:numbered-list ::format]) (get-in format-options [:bullet-list ::format])})

(def hotkeys
  (reduce (fn [m {::keys [format shortcut type]}] (cond-> m shortcut (assoc (str/join "+" shortcut) [type format])))
    {}
    (vals format-options)))

(defn is-hotkey [hot-key event] (isHotkey hot-key event))

(defsc Shortcut [this {:keys [shortcut]}]
  (str/join
    "+"
    (mapv
      (fn [s]
        (cond
          (and (= s "mod") mac?) "⌘"
          (= s "mod") "Ctrl"
          (and (= s "alt") mac?) "⌥"
          (= s "alt") "Alt"
          (and (= s "shift") mac?) "⇧"
          (= s "shift") "Shift"
          :default (str/upper-case s)))
      shortcut)))

(def ui-shortcut (comp/factory Shortcut))

(defsc TooltipShortcutLozenge [this {:keys [shortcut]}]
  ;; This CSS is stolen from the Atlassian editor
  {:css
     [[:.lozenge
       {:background-color
          (->
            (garden-color/hex->hsl colors/N40)
            (garden-color/opacify 0.2))
        :color colors/N0
        :line-height "12px"
        :font-size "11.67px"
        :align-self "flex-end"
        :border-radius "1px"
        :padding "0 2px"}]]}
  (dom/span :.lozenge (ui-shortcut {:shortcut shortcut})))

(def ui-tooltip-shortcut-lozenge (comp/factory TooltipShortcutLozenge))

(defsc ShortcutLozenge [this {:keys [shortcut]}]
  ;; This CSS is stolen from the Atlassian editor
  {:css
     [[:.lozenge
       {:background-color
          (->
            (garden-color/hex->hsl colors/N40)
            (garden-color/opacify 0.5))
        :color colors/N100
        :line-height "12px"
        :font-size "11.67px"
        :align-self "flex-end"
        :border-radius "3px"
        :padding "4px"}]]}
  (dom/span :.lozenge (ui-shortcut {:shortcut shortcut})))

(def ui-shortcut-lozenge (comp/factory ShortcutLozenge))

(defn block-active? [editor format]
  (->
    (.nodes Editor editor #js {:match (fn [n] (= format (gobj/get n "type")))})
    (es6-iterator-seq)
    first
    some?))

(defn list-type? [format] (contains? list-types format))

(defn toggle-block [editor format]
  (let [active? (block-active? editor format)
        block-list-type? (list-type? format)]
    ;; Split al nodes when we want to add a list
    (.unwrapNodes
      Transforms
      editor
      #js
       {:match #(list-type? (gobj/get % "type"))
        :split true})
    (.setNodes
      Transforms
      editor
      #js
       {:type
          (cond
            ;; When active disable
            active? (get-in format-options [:paragraph ::format])
            ;; When we want a list type make all items list-items
            (and (not active?) block-list-type?) (get-in format-options [:list-item ::format])
            ;; Apply normal block
            :default format)})
    ;; When not active an list type wrap them in the list type
    (when (and block-list-type? (not active?))
      (let [block #js
                   {:type format
                    :children []}]
        (.wrapNodes Transforms editor block)))))

(defn mark-active? [editor format] (let [marks (.marks Editor editor)] (when marks (true? (gobj/get marks format)))))

(defn toggle-mark [editor format]
  (if (mark-active? editor format) (.removeMark Editor editor format) (.addMark Editor editor format true)))

(defn on-key-down [editor event]
  (run!
    (fn [[hotkey [type format]]]
      (when (is-hotkey hotkey event)
        (.preventDefault event)
        (cond
          (= type ::mark) (toggle-mark editor format)
          (= type ::block) (toggle-block editor format))))
    hotkeys))

(defn format-option-active? [editor format-option]
  (let [{::keys [format]
         format-type ::type}
          (get format-options format-option)]
    (case format-type
      ::block (block-active? editor format)
      ::mark (mark-active? editor format)
      (log/warn "format-option-not-found" {:format-option format-option}))))

(defn toggle-format-option [editor format-option]
  (let [{::keys [format]
         format-type ::type}
          (get format-options format-option)]
    (case format-type
      ::block (toggle-block editor format)
      ::mark (toggle-mark editor format))))

(defsc EditorButton [this {:keys [icon format label]}]
  {:use-hooks? true}
  (comp/with-parent-context
    this
    (let [editor (useSlate)
          {::keys [shortcut]} (get format-options format)]
      (tooltip/ui-tooltip
        {:position "top"
         :content (dom/span label " " (when shortcut (ui-tooltip-shortcut-lozenge {:shortcut shortcut})))}
        (comp/with-parent-context
          this
          (button/ui-atlaskit-button
            {:appearance "subtle"
             :isSelected (format-option-active? editor format)
             :onMouseDown (fn [event] (.preventDefault event) (toggle-format-option editor format))
             :iconBefore icon}))))))

(def ui-editor-button (comp/factory EditorButton))

(defsc Toolbar [this props]
  {:css
     [[:.toolbar
       {:align-items "center"
        :display "flex"
        :height "auto"
        :padding "8px 8px 0px 20px"}]]}
  (dom/div :.toolbar (comp/children this)))

(def ui-toolbar (comp/factory Toolbar))

(defn render-leaf [args]
  (let [{:keys [attributes children leaf]} (bean args)]
    (dom/span
      attributes
      (cond-> children
        (gobj/get leaf "bold") dom/strong
        (gobj/get leaf "italic") dom/em
        (gobj/get leaf "underline") dom/u
        (gobj/get leaf "code") dom/pre
        (gobj/get leaf "strikethrough") dom/s
        (gobj/get leaf "subscript") dom/sub
        (gobj/get leaf "superscript") dom/sup))))

(defn render-element [args]
  (let [{:keys [attributes children element]} (bean args)]
    (case (gobj/get element "type")
      "blockquote" (dom/blockquote attributes children)
      "heading-1" (dom/h1 attributes children)
      "heading-2" (dom/h2 attributes children)
      "heading-3" (dom/h3 attributes children)
      "heading-4" (dom/h4 attributes children)
      "heading-5" (dom/h5 attributes children)
      "heading-6" (dom/h6 attributes children)
      "bullet-list" (dom/ul attributes children)
      "numbered-list" (dom/ol attributes children)
      "list-item" (dom/li attributes children)
      (dom/p attributes children))))

(defsc MoreFormattingOption [this {:keys [label format on-select]}]
  {:use-hooks? true}
  (comp/with-parent-context
    this
    (let [editor (useSlate)
          {::keys [shortcut]} (get format-options format)]
      (menu/ui-button-item
        #js
         {:isSelected (format-option-active? editor format)
          :onMouseDown (fn [e] (.preventDefault e) (toggle-format-option editor format) (when on-select (on-select)))
          :elemAfter (when shortcut (ui-shortcut-lozenge {:shortcut shortcut}))}
        label))))

(defsc Separator [this _]
  {:css
     [[:.separator
       {:width "1px"
        :height "24px"
        :display "inline-block"
        :background "rgb(235, 236, 240)"
        :margin "0px 8px"}]]}
  (dom/div :.separator))

(def ui-separator (comp/factory Separator))

(def ui-more-formatting-option (comp/factory MoreFormattingOption {:keyfn :format}))

(defsc AtlasEditor [this {:keys [editor value on-change]}]
  {:css-include [Toolbar Separator ShortcutLozenge TooltipShortcutLozenge]
   :initLocalState
     (fn []
       {:text-styles-dropdown-open? false
        :more-formatting-open? false})
   :css
     [[:.wrapper
       {:border-color colors/N40
        :border-radius "3px"
        :border-width "1px"
        :border-style "solid"
        :min-width "272px"
        :min-height "150px"
        :display "flex"
        :flex-direction "column"}
       [:blockquote
        {:box-sizing "border-box"
         :padding-left "16px"
         :border-left (str "2px solid " colors/N40)
         :margin "1.143rem 0 0"}
        [:&:before {:content "\"\""}]
        [:&:after {:content "\"\""}]]]
      [:.editable-wrapper {:padding "20px"}]
      [:.icon {:margin "0 -2px"}]]}
  (dom/div
    :.wrapper
    (ui-slate
      {:editor editor
       :value value
       :onChange on-change}
      (ui-toolbar
        {}
        (popup/ui-popup
          {:isOpen (comp/get-state this :text-styles-dropdown-open?)
           :onClose (fn [] (comp/update-state! this assoc :text-styles-dropdown-open? false))
           :placement "bottom-start"
           :trigger
             (fn [triggerProps]
               (comp/with-parent-context
                 this
                 (tooltip/ui-tooltip
                   {:content (tr "Text styles")
                    :position "top"}
                   (button/ui-atlaskit-button
                     (utils/js-spread
                       triggerProps
                       #js
                        {:appearance "subtle"
                         :isSelected (comp/get-state this :text-styles-dropdown-open?)
                         :onClick #(comp/update-state! this update :text-styles-dropdown-open? not)
                         :iconAfter (chevron-down/ui-icon {:label (tr "Text styles")})})
                     (tr "Normal text")))))
           :content
             (fn []
               (comp/with-parent-context
                 this
                 (menu/ui-menu-group
                   {}
                   (menu/ui-section
                     {}
                     (mapv
                       (fn [props]
                         (ui-more-formatting-option
                           (assoc props :on-select #(comp/update-state! this update :text-styles-dropdown-open? not))))
                       [{:format :paragraph
                         :label "Normal text"}
                        {:format :heading-1
                         :label (dom/h1 (tr "Heading 1"))}
                        {:format :heading-2
                         :label (dom/h2 (tr "Heading 2"))}
                        {:format :heading-3
                         :label (dom/h2 (tr "Heading 3"))}
                        {:format :heading-4
                         :label (dom/h2 (tr "Heading 4"))}
                        {:format :heading-5
                         :label (dom/h2 (tr "Heading 5"))}
                        {:format :heading-6
                         :label (dom/h2 (tr "Heading 6"))}])))))})
        (ui-separator {})
        (ui-editor-button
          {:label "Bold"
           :icon (dom/span :.icon (bold/ui-icon {:label (tr "Make bold")}))
           :format :bold})
        (ui-editor-button
          {:label "Italic"
           :icon (dom/span :.icon (italic/ui-icon {:label (tr "Make italic")}))
           :format :italic})
        (popup/ui-popup
          {:isOpen (comp/get-state this :more-formatting-open?)
           :onClose (fn [] (comp/update-state! this assoc :more-formatting-open? false))
           :placement "bottom-start"
           :trigger
             (fn [triggerProps]
               (comp/with-parent-context
                 this
                 (tooltip/ui-tooltip
                   {:content (tr "More formatting")
                    :position "top"}
                   (button/ui-atlaskit-button
                     (utils/js-spread
                       triggerProps
                       #js
                        {:appearance "subtle"
                         :isSelected (comp/get-state this :more-formatting-open?)
                         :onClick #(comp/update-state! this update :more-formatting-open? not)
                         :iconBefore (more/ui-icon {:label (tr "More formatting")})})))))
           :content
             (fn []
               (comp/with-parent-context
                 this
                 (menu/ui-menu-group
                   {}
                   (menu/ui-section
                     {}
                     (mapv ui-more-formatting-option
                       [{:format :underline
                         :label (tr "Underline")}
                        {:format :strikethrough
                         :label (tr "Strikethrough")}
                        {:format :code
                         :label (tr "Code")}
                        {:format :subscript
                         :label (tr "Subscript")}
                        {:format :superscript
                         :label (tr "Superscript")}])))))})
        (ui-separator {})
        (ui-editor-button
          {:label (tr "Bullet list")
           :icon (dom/span :.icon (bullet-list/ui-icon {:label (tr "Bullet list")}))
           :format :bullet-list})
        (ui-editor-button
          {:label (tr "Numbered list")
           :icon (dom/span :.icon (number-list/ui-icon {:label (tr "Number list")}))
           :format :numbered-list})
        (ui-separator {})
        (ui-editor-button
          {:label (tr "Quote")
           :icon (dom/span :.icon (quote/ui-icon {:label (tr "Quote")}))
           :format :blockquote}))
      (dom/div
        :.editable-wrapper
        (ui-editable
          {:renderLeaf render-leaf
           :onKeyDown #(on-key-down editor %)
           :renderElement render-element})))))

(def ui-editor (comp/factory AtlasEditor))

(defn create-editor [] (withHistory (withReact (createEditor) [])))
