(ns fulcro-atlaskit.css-constants)

(defn important [s] (str s " !important"))

; Spacing
(def spacing-1 "4px")
(def spacing-2 "8px")
(def spacing-3 "12px")
(def spacing-4 "16px")
(def spacing-5 "20px")
(def spacing-6 "24px")
(def spacing-7 "28px")
(def spacing-8 "32px")
(def spacing-9 "36px")
(def spacing-10 "40px")
(def spacing-11 "44px")
(def spacing-12 "48px")
(def spacing-13 "52px")
(def spacing-14 "56px")
(def spacing-15 "60px")

; Fonts
(def default-font-size "14px")

; Z-index
(def z-index-lowest 0)
(def z-index-low 500)
(def z-index-medium 1000)
(def z-index-high 5000)
(def z-index-highest 9999)

(def truncate-rules
  {:overflow "hidden"
   :text-overflow "ellipsis"
   :white-space "nowrap"})

(def inline-dialog-shadow "0 4px 8px -2px rgba(9, 30, 66, 0.31), 0 0 1px rgba(9, 30, 66, 0.31)")
(def focus-shadow "rgba(38, 132, 255, 0.6) 0px 0px 0px 2px")

(def vertically-align-center
  {:display "flex"
   :align-items "center"})
