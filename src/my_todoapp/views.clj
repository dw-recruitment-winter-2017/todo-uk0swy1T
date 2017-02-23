(ns my-todoapp.views
  (:require [my-todoapp.db :as db]
            [clojure.string :as str]
            [hiccup.page :as hic-p]))

(defn gen-page-head
  [title]
  [:head
   [:title (str "Todo list: " title)]
   [:link
    {:href
     "https://upload.wikimedia.org/wikipedia/commons/thumb/5/5d/Clojure_logo.svg/220px-Clojure_logo.svg.png",
     :type "image/png",
     :rel "icon"}]
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
   [:div {:class "main"}
   [:h1 "Home"]
   [:p "To do list items"]
   [:ul
   (for [loc all-items]
     [:li (when (:done loc) {:class "thisdone"}) (:todo loc)])]
   [:form {:action "/" :method "POST"}
   [:p "task: " [:input {:type "text" :name "x"}] [:input {:type "submit" :value "add item"}]]]])))

(defn add-todo-results-page
  [{:keys [x]}]
  (let [id (db/add-todo-to-db x)]
    (hic-p/html5
     (gen-page-head "Added a todo")
     header-links
     [:h1 "Added a todo"]
     [:p "Back to the "
      [:a {:href (str "/")} "list"]
      "."])))

(defn about-page
  []
  (hic-p/html5
    (gen-page-head "About")
    header-links
    [:div {:class "main"}
    [:h1 "About"]
    [:p "First crack at Clojure"]
    [:p "If a task is complete it will be have a line through it"]]))
