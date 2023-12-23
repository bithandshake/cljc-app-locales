
(ns app-locales.name.utils
    (:require [fruits.vector.api :as vector]
              [app-locales.name.config :as name.config]
              [app-locales.core.state :as core.state]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn country-name-order
  ; @param (keyword)(opt) country-code
  ; Default: @SELECTED-LOCALE
  ;
  ; @usage
  ; (country-name-order :us)
  ; =>
  ; :normal
  ;
  ; @usage
  ; (country-name-order :hu)
  ; =>
  ; :reversed
  ;
  ; @return (keyword)
  ; :normal, :reversed
  ([]
   (country-name-order @core.state/SELECTED-LOCALE))

  ([country-code]
   (if (vector/contains-item? name.config/REVERSED-NAME-ORDER-COUNTRIES country-code)
       (-> :reversed)
       (-> :normal))))

(defn format-name
  ; @param (string) first-name
  ; @param (string) last-name
  ; @param (keyword)(opt) country-code
  ; Default: @SELECTED-LOCALE
  ;
  ; @usage
  ; (format-name "John" "Doe" :us)
  ; =>
  ; "John Doe"
  ;
  ; @usage
  ; (format-name "John" "Doe" :hu)
  ; =>
  ; "Doe John"
  ;
  ; @return (string)
  ([first-name last-name]
   (format-name first-name last-name @core.state/SELECTED-LOCALE))

  ([first-name last-name country-code]
   (case (country-name-order country-code)
         :reversed (str last-name  " " first-name)
         :normal   (str first-name " " last-name))))

(defn name-initials
  ; @param (string) first-name
  ; @param (string) last-name
  ; @param (keyword)(opt) country-code
  ; Default: @SELECTED-LOCALE
  ;
  ; @usage
  ; (name-initials "John" "Doe" :us)
  ; =>
  ; "JD"
  ;
  ; @usage
  ; (name-initials "John" "Doe" :hu)
  ; =>
  ; "DJ"
  ;
  ; @return (string)
  ([first-name last-name]
   (name-initials first-name last-name @core.state/SELECTED-LOCALE))

  ([first-name last-name country-code]
   (case (country-name-order country-code)
         :reversed (str (first last-name)  (first first-name))
         :normal   (str (first first-name) (first last-name)))))
