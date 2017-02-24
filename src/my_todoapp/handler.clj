(ns my-todoapp.handler
  (:require [my-todoapp.views :as views]
            [compojure.core :as cc]
            [compojure.handler :as handler]
            [compojure.route :as route]))

(cc/defroutes app-routes
  (cc/GET "/"
       {params :params}
        (views/home-page params))
  (cc/GET "/about"
       []
       (views/about-page))
  (cc/POST "/add/"
        {params :params}
        (views/home-page params))
  (cc/POST "/toggle/:id"
           [id]
           (views/toggle-todo-page id))
  (cc/POST "/delete/:id"
           [id]
           (views/delete-todo-page id))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))
