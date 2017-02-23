(ns my-todoapp.views
  (:require [my-todoapp.db :as db]
            [clojure.string :as str]
            [hiccup.page :as hic-p]))

(defn gen-page-head
  [title]
  [:head
   [:title (str "Todo list: " title)]
   (hic-p/include-css "/css/styles.css")])

(def header-links
  [:div#header-links
   "[ "
   [:a {:href "/"} "Home"]
   " | "
   [:a {:href "/about"} "About"]
   " ]"])

(defn home-page
  []
  (let [all-items (db/get-all-todos)]
  (hic-p/html5
   (gen-page-head "Home")
   header-links
   [:h1 "Home"]
   [:p "To do list items"]
   [:ul
   (for [loc all-items]
     [:li (:todo loc)])])))

(defn about-page
  []
  (hic-p/html5
    (gen-page-head "About")
    header-links
    [:h1 "About"]
    [:p "First crack at Clojure"]))
