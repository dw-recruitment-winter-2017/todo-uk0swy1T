(ns my-todoapp.db
  (:require [clojure.java.jdbc :as sql]))

(def db-spec {:classname "org.h2.Driver"
              :subprotocol "h2:file"
              :subname "db/todo-db"})

(defn add-todo-to-db
  [x]
  (let [results (sql/with-connection db-spec
                  (sql/insert-record :locations
                                     {:todo x :done false}))]
    (assert (= (count results) 1))
    (first (vals results))))

(defn get-todo
  [todo-id]
  (let [results (sql/with-connection db-spec
                  (sql/with-query-results res
                    ["select todo, done from locations where id = ?" todo-id]
                    (doall res)))]
    (assert (= (count results) 1))
    (first results)))

(defn get-all-todos
  []
  (let [results (sql/with-connection db-spec
                  (sql/with-query-results res
                    ["select id, todo, done from locations"]
                    (doall res)))]
    results))
