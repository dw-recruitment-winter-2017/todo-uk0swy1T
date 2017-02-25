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
  [{:keys [x]}]
  (if-not (nil? x) (let [id (db/add-todo-to-db x)]))
  (let [all-items (db/get-all-todos)]
  (hic-p/html5
   (gen-page-head "Home")
   header-links
   [:div {:class "main" :id "todoapp"}
   [:header {:id "header"}
   [:h1 "Todos"]
   [:form {:action "/add/" :method "POST"}
   [:p {:id "new-todo"} [:input {:type "text" :name "x"}] [:input {:type "submit" :value "add item"}]]]
   [:ul {:id "todo-list"}
   (for [loc all-items]
     (if (:done loc)
       [:div {:class "view"} [:li {:id (str (:id loc)) :class "thisdone"} (:todo loc) [:form {:action (str "/toggle/" (:id loc)) :method "POST"}
      [:input {:type "submit" :value "toggle"}]] [:form {:action (str "/delete/" (:id loc)) :method "POST"}
      [:input {:type "submit" :value "delete"}]]]]
       [:div {:class "view"} [:li {:id (str (:id loc))} (:todo loc) [:form {:action (str "/toggle/" (:id loc)) :method "POST"}
      [:input {:type "submit" :value "toggle"}]] [:form {:action (str "/delete/" (:id loc)) :method "POST"}
      [:input {:type "submit" :value "delete"}]]]]))]]])))

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

(defn toggle-todo-page
  [x]
  (let [itemdet (first (db/get-todo x))]
    (let [ans (db/completed-todo x (false? (:done itemdet)))]
      (hic-p/html5
       (gen-page-head "Toggle a todo")
       header-links
       [:div {:class "main" :id "todoapp"}
       [:header {:id "header"}
       [:h1 "Toggle a todo"]
       [:div {:class "view"}
       [:p "You have marked '" (:todo itemdet) "' as " (if (false? (:done itemdet))
       "done."
       "incomplete.") ]
       [:p "Back to the "
        [:a {:href (str "/")} "list"]
        "."]]]]))))

(defn delete-todo-page
  [x]
  (let [id (db/delete-todo x)]
    (hic-p/html5
     (gen-page-head "Deleted a todo")
     header-links
     [:div {:class "main" :id "todoapp"}
     [:header {:id "header"}
     [:h1 "Deleted a todo"]
     [:div {:class "view"}
     [:p "Back to the "
      [:a {:href (str "/")} "list"]
      "."]]]])))

(defn about-page
  []
  (hic-p/html5
    (gen-page-head "About")
    header-links
    [:div {:class "main" :id "todoapp"}
    [:header {:id "header"}
    [:h1 "About"]
    [:div {:class "view"}
    [:p "First crack at Clojure"]
    [:p "If a task is complete it will have a line through it"]
    [:p "Style inspired by " [:a {:href (str "http://todomvc.com/")} "todomvc.com"] "."]]]]))
