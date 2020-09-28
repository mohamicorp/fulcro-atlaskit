(ns fulcro-atlaskit.editor
  (:require
    [com.fulcrologic.fulcro.algorithms.react-interop :as react-interop]
    [com.fulcrologic.fulcro.components :as comp :refer [defsc]]
    [com.fulcrologic.guardrails.core :refer [>def >defn]]
    [com.fulcrologic.fulcro-i18n.i18n :refer [tr]]
    [com.fulcrologic.fulcro-css.localized-dom :as dom]
    [fulcro-atlaskit.button :as button]
    [fulcro-atlaskit.utils :as utils]
    [fulcro-atlaskit.colors :as colors]
    [fulcro-atlaskit.popup :as popup]
    [fulcro-atlaskit.icon.editor.bold :as bold]
    [fulcro-atlaskit.icon.editor.italic :as italic]
    [fulcro-atlaskit.icon.editor.more :as more]
    [fulcro-atlaskit.css-constants :as css-constants]
    [fulcro-atlaskit.icon.editor.quote :as quote]
    [fulcro-atlaskit.icon.editor.number-list :as number-list]
    [fulcro-atlaskit.icon.editor.bullet-list :as bullet-list]
    [fulcro-atlaskit.icon.chevron-down :as chevron-down]
    [fulcro-atlaskit.icon.editor.align-right :as align-right]
    [fulcro-atlaskit.icon.editor.align-left :as align-left]
    [fulcro-atlaskit.icon.editor.align-center :as align-center]
    [fulcro-atlaskit.icon.trash :as trash]
    [fulcro-atlaskit.tooltip :as tooltip]
    [fulcro-atlaskit.menu :as menu]
    [garden.color :as garden-color]
    [fulcro-atlaskit.icon.editor.link :as link]
    [fulcro-atlaskit.textfield :as textfield]
    [cljs-bean.core :refer [bean ->js]]
    [goog.object :as gobj]
    [goog.events :as events]
    [goog.events.EventType :as event-type]
    ["slate" :refer [Editor createEditor Transforms]]
    ["slate-history" :refer [withHistory]]
    ["slate-hyperscript" :as slate-hs]
    ["is-hotkey" :default isHotkey]
    ["slate-react" :as sr :refer [ReactEditor Slate Editable withReact useSlate]]
    [clojure.string :as str]
    [taoensso.timbre :as log]
    [goog.userAgent :as guser-agent]))

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
   :align-right
     {::type ::block-style
      ::format "align-right"}
   :align-left
     {::type ::block-style
      ::format "align-left"}
   :align-center
     {::type ::block-style
      ::format "align-center"}
   :link
     {::type ::block
      ::format "link"
      ::shortcut ["mod" "K"]}
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

(defn block-style-active? [editor format]
  (->
    (.nodes Editor editor #js {:match (fn [n] (= format (gobj/get n "align")))})
    (es6-iterator-seq)
    first
    some?))

(defn list-type? [format] (contains? list-types format))

(defmulti toggle-block
  (fn [editor format]
    (cond
      (list-type? format) :list
      (= "link" format) :link)))

(defmethod toggle-block :default
  [editor format]
  (let [active? (block-active? editor format)]
    (.setNodes
      Transforms
      editor
      #js {:type (if active? (get-in format-options [:paragraph ::format]) format)})))

(defmethod toggle-block :list
  [editor format]
  (let [active? (block-active? editor format)]
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
       {:type (if active? (get-in format-options [:paragraph ::format]) (get-in format-options [:list-item ::format]))})
    (when-not active?
      (let [block #js
                   {:type format
                    :children []}]
        (.wrapNodes Transforms editor block)))))

(defmethod toggle-block :link
  [editor format]
  (let [selection (.-selection editor)
        anchor (.-anchor selection)
        focus (.-focus selection)
        something-selected? (not= (.-offset anchor) (.-offset focus))]
    (if (block-active? editor format)
      (.unwrapNodes
        Transforms
        editor
        #js
         {:match #(= "link" (gobj/get % "type"))
          :split true})
      (when something-selected?
        (.wrapNodes
          Transforms
          editor
          #js
           {:type format
            :open true
            :children []}
          #js {:split true})))))

(defn toggle-block-style [editor format]
  (cond (str/starts-with? format "align") (.setNodes Transforms editor #js {"align" format})))

(defn mark-active? [editor format] (let [marks (.marks Editor editor)] (when marks (true? (gobj/get marks format)))))

(defn toggle-mark [editor format]
  (if (mark-active? editor format) (.removeMark Editor editor format) (.addMark Editor editor format true)))

(defn save-hotkey? [event] (is-hotkey "mod+enter" event))

(defn on-key-down [editor event on-save]
  (if (and on-save (save-hotkey? event))
    (do (.preventDefault event) (.blur (.-target event)) (on-save))
    (run!
      (fn [[hotkey [type format]]]
        (when (is-hotkey hotkey event)
          (.preventDefault event)
          (cond
            (= type ::mark) (toggle-mark editor format)
            (= type ::block) (toggle-block editor format))))
      hotkeys)))

(defn format-option-active? [editor format-option]
  (let [{::keys [format]
         format-type ::type}
          (get format-options format-option)]
    (case format-type
      ::block-style (block-style-active? editor format)
      ::block (block-active? editor format)
      ::mark (mark-active? editor format)
      (log/warn "format-option-not-found" {:format-option format-option}))))

(defn toggle-format-option [editor format-option]
  (let [{::keys [format]
         format-type ::type}
          (get format-options format-option)]
    (case format-type
      ::block-style (toggle-block-style editor format)
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
             :onClick (fn [event] (.preventDefault event) (js/setTimeout (fn [] (toggle-format-option editor format))))
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

(defn block-style [element]
  (let [{:keys [align]} (bean element)] (cond-> {} align (assoc :textAlign (second (str/split align #"-"))))))

(defn update-url-node [editor element js-diff]
  (.setNodes Transforms editor js-diff #js {:at (.findPath ReactEditor editor element)}))

(defsc URLEditor [this {::keys [editor url element attributes]}]
  {:css
     [[:.url
       {:padding css-constants/spacing-2
        :width "250px"
        :display "flex"
        :align-items "center"}]
      [:.input
       {:margin-right css-constants/spacing-2
        :width "100%"}]
      [:.link {:cursor "pointer"}]]}
  (let [open? (.-open element)]
    (popup/ui-popup
      {:isOpen open?
       :onClose (fn [e] (update-url-node editor element #js {"open" false}))
       :placement "bottom-center"
       :trigger
         (fn [triggerProps]
           (comp/with-parent-context
             this
             (dom/span
               triggerProps
               (dom/a
                 :.link
                 (utils/js-spread
                   attributes
                   #js
                    {:title url
                     :onClick (fn [] (update-url-node editor element #js {"open" true}))})
                 (comp/children this)))))
       :content
         (fn [args]
           (comp/with-parent-context
             this
             (dom/div
               :.url
               (dom/div
                 :.input
                 (textfield/ui-textfield
                   {:value (or url "")
                    :onFocus
                      (fn [e]
                        (some->
                          e
                          (.-target)
                          (.select)))
                    :ref (fn [ref] ((gobj/get args "setInitialFocusRef") ref) (gobj/set this "input" ref))
                    :onKeyDown (fn [e] (when (= "Enter" (.-key e)) (update-url-node editor element #js {"open" false})))
                    :onChange
                      (fn [e]
                        (let [value (.-value (.-target e))] (update-url-node editor element #js {"url" value})))}))
               (button/ui-button
                 {:iconBefore (trash/ui-icon)
                  :onClick
                    (fn [] (.unwrapNodes Transforms editor #js {"at" (.findPath ReactEditor editor element)}))}))))})))


(def ui-url-editor (comp/factory URLEditor))

(defn render-element [args this editor read-only?]
  (let [{:keys [attributes children element]} (bean args)
        style (block-style element)]
    (gobj/set attributes "style" (->js style))
    (case (gobj/get element "type")
      "blockquote" (dom/blockquote attributes children)
      "heading-1" (dom/h1 attributes children)
      "heading-2" (dom/h2 attributes children)
      "heading-3" (dom/h3 attributes children)
      "heading-4" (dom/h4 attributes children)
      "heading-5" (dom/h5 attributes children)
      "heading-6" (dom/h6 attributes children)
      "image"
        (dom/div
          attributes
          (dom/img
            {:src (.-url element)
             :width (.-width element)
             :height (.-height element)})
          children)
      "link"
        (if read-only?
          (dom/a {:href (.-url element)} children)
          (comp/with-parent-context
            this
            (ui-url-editor
              {::editor editor
               ::element element
               ::attributes attributes
               ::url (.-url element)}
              children)))
      "bullet-list" (dom/ul attributes children)
      "numbered-list" (dom/ol attributes children)
      "list-item" (dom/li attributes children)
      "table" (dom/table attributes children)
      "thead" (dom/thead attributes children)
      "tbody" (dom/tbody attributes children)
      "tr" (dom/tr attributes children)
      "td" (dom/td attributes children)
      "th" (dom/th attributes children)
      (dom/p attributes children))))

(defn format->label [format]
  (case format
    :paragraph (tr "Normal text")
    :heading-1 (tr "Heading 1")
    :heading-2 (tr "Heading 2")
    :heading-3 (tr "Heading 3")
    :heading-4 (tr "Heading 4")
    :heading-5 (tr "Heading 5")
    :heading-6 (tr "Heading 6")))

(defn format->html-label [format]
  (let [label (format->label format)]
    (condp format = :paragraph label :heading-1 (dom/h1 label) format (dom/h2 label))))

(def text-formats [:paragraph :heading-1 :heading-2 :heading-3 :heading-4 :heading-5 :heading-6])

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

(defsc AlignmentMenu [this {:keys [active-alignment]}]
  (popup/ui-popup
    {:isOpen (comp/get-state this :alignment-open?)
     :onClose (fn [] (comp/update-state! this assoc :alignment-open? false))
     :placement "bottom-start"
     :trigger
       (fn [triggerProps]
         (comp/with-parent-context
           this
           (tooltip/ui-tooltip
             {:content (tr "Alignment")
              :position "top"}
             (button/ui-atlaskit-button
               (utils/js-spread
                 triggerProps
                 #js
                  {:appearance "subtle"
                   :iconBefore
                     (case active-alignment
                       "align-left" (align-left/ui-icon {:label (tr "Align left")})
                       "align-right" (align-right/ui-icon {:label (tr "Align right")})
                       "align-center" (align-center/ui-icon {:label (tr "Align center")})
                       (align-left/ui-icon))
                   :isSelected (comp/get-state this :alignment-open?)
                   :onClick #(comp/update-state! this update :alignment-open? not)})))))
     :content
       (fn []
         (comp/with-parent-context
           this
           (dom/div
             {:style {:display "flex"}}
             (ui-editor-button
               {:label (tr "Align left")
                :icon (align-left/ui-icon {:label (tr "Align left")})
                :format :align-left})
             (ui-editor-button
               {:label (tr "Align center")
                :icon (align-center/ui-icon {:label (tr "Align center")})
                :format :align-center})
             (ui-editor-button
               {:label (tr "Align right")
                :icon (align-right/ui-icon {:label (tr "Align right")})
                :format :align-right}))))}))

(def ui-alignment-menu (comp/factory AlignmentMenu))

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

(defsc AtlasEditor [this {:keys [editor value on-change autofocus? on-focus on-blur on-save]}]
  {:css-include [Toolbar Separator ShortcutLozenge TooltipShortcutLozenge URLEditor]
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
       ["div[role=textbox]" {:box-shadow (css-constants/important "none")}]
       [:blockquote
        {:box-sizing "border-box"
         :padding-left "16px"
         :border-left (str "2px solid " colors/N40)
         :margin "1.143rem 0 0"}
        [:&:before {:content "\"\""}]
        [:&:after {:content "\"\""}]]]
      [:.editable-wrapper
       {:padding "20px"
        :overflow "hidden"}]
      [:.icon {:margin "0 -2px"}]
      [:$atlaskit-portal {:z-index (css-constants/important css-constants/z-index-highest)}]]}
  (dom/div
    :.wrapper
    (ui-slate
      {:editor editor
       :value value
       :onChange (fn [change] (on-change change))}
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
                     (or
                       (some
                         (fn [format] (when (format-option-active? editor format) (format->label format)))
                         text-formats)
                       (format->label (first text-formats)))))))
           :content
             (fn []
               (comp/with-parent-context
                 this
                 (menu/ui-menu-group
                   {}
                   (menu/ui-section
                     {}
                     (mapv
                       (fn [text-format]
                         (ui-more-formatting-option
                           {:format text-format
                            :label (format->html-label text-format)
                            :on-select #(comp/update-state! this update :text-styles-dropdown-open? not)}))
                       text-formats)))))})
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
                     (mapv
                       (fn [props]
                         (ui-more-formatting-option
                           (assoc props :on-select #(comp/update-state! this update :more-formatting-open? not))))
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
        (ui-alignment-menu
          {:active-alignment
             (some
               (fn [format] (when (block-style-active? editor format) format))
               ["align-left" "align-right" "align-center"])})
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
           :format :blockquote})
        (ui-editor-button
          {:label (tr "Link")
           :icon (dom/span :.icon (link/ui-icon {:label (tr "Link")}))
           :format :link}))
      (dom/div
        :.editable-wrapper
        (ui-editable
          {:renderLeaf render-leaf
           :autoFocus autofocus?
           :onFocus on-focus
           :onBlur on-blur
           :onKeyDown #(on-key-down editor % on-save)
           :renderElement #(render-element % this editor false)})))))

(def ui-editor (comp/factory AtlasEditor))

(defsc Display [this {:keys [editor value]}]
  (ui-slate
    {:editor editor
     :value value}
    (ui-editable
      {:renderLeaf render-leaf
       :readOnly true
       :renderElement #(render-element % this editor true)})))

(def ui-display (comp/factory Display))

(defn el->attributes [el]
  (case (.-nodeName el)
    "A"
      {:type "link"
       :url (.getAttribute el "href")}
    "BLOCKQUOTE" {:type "quote"}
    "H1" {:type "heading-1"}
    "H2" {:type "heading-2"}
    "H3" {:type "heading-3"}
    "H4" {:type "heading-4"}
    "H5" {:type "heading-5"}
    "H6" {:type "heading-6"}
    "IMG"
      {:type "image"
       :url (.getAttribute el "src")
       :width (.getAttribute el "width")
       :height (.getAttribute el "height")}
    "LI" {:type "list-item"}
    "OL" {:type "numbered-list"}
    "P" {:type "paragraph"}
    "PRE" {:type "code"}
    "UL" {:type "bulleted-list"}
    "TABLE" {:type "table"}
    "THEAD" {:type "thead"}
    "TBODY" {:type "tbody"}
    "TH" {:type "th"}
    "TR" {:type "tr"}
    "TD" {:type "td"}
    nil))

(defn text-el->attributes [el]
  (case (.-nodeName el)
    "CODE" {:code true}
    "DEL" {:strikethrough true}
    "EM" {:italic true}
    "I" {:italic true}
    "S" {:strikethrough true}
    "STRONG" {:bold true}
    "U" {:underline true}
    nil))

(declare deserialize-element)

(defn code? [el]
  (and
    (= "PRE" (.-nodeName el))
    (some->
      el
      (.-childNodes)
      (seq)
      first
      (.-nodeName)
      (= "CODE"))))

(defn body-element [el children] (when (= (.-nodeName el) "BODY") (slate-hs/jsx "fragment" #js {} (->js children))))

(defn text-element [el children]
  (when-let [attributes (text-el->attributes el)]
    (map (fn [child] (slate-hs/jsx "text" (->js attributes) child)) (->js children))))

(defn other-element [el children]
  (when-let [attributes (el->attributes el)]
    (slate-hs/jsx "element" (->js attributes) (if (= "IMG" (.-nodeName el)) #js [#js {"text" ""}] (->js children)))))

(defn deserialize-node-tree [el]
  (let [parent (if (code? el) (first (seq (js/Array.from (.-childNodes el)))) el)
        children (seq (flatten (map deserialize-element (js/Array.from (.-childNodes parent)))))]
    (or (body-element el children) (text-element el children) (other-element el children) children)))

(defn deserialize-element [el]
  (let [node-type (.-nodeType el)
        node-name (.-nodeName el)]
    (cond
      (= node-type 3) (.-textContent el)
      (not= node-type 1) nil
      (= node-name "BR") "\n"
      :else (deserialize-node-tree el))))

(defn with-html [editor]
  (let [inline? (.-isInline editor)
        void? (.-isVoid editor)
        insert-data (.-insertData editor)]
    (gobj/set editor "isInline" (fn [el] (or (= "link" (.-type el)) (inline? el))))
    (gobj/set editor "isVoid" (fn [el] (or (= "image" (.-type el)) (void? el))))
    (gobj/set
      editor
      "insertData"
      (fn [data]
        (let [html (.getData data "text/html")]
          (if html
            (let [parsed (.parseFromString (js/DOMParser.) html "text/html")
                  fragment (deserialize-element (.-body parsed))]
              (.insertFragment Transforms editor fragment))
            (insert-data data)))))
    editor))

(defn create-editor [] (with-html (withHistory (withReact (createEditor) []))))

(defn reset-selection! [editor] (.select Transforms editor #js [0]))
