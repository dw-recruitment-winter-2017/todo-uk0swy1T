(ns my-todoapp.db
  (:require [clojure.java.jdbc :as sql]))

(def db-spec {:classname "org.h2.Driver"
              :subprotocol "h2:file"
              :subname "db/todo-db"})

(defn add-todo-to-db
  [x]
  (let [results (sql/insert! db-spec :locations
                                     {:todo x :done false})]
    (vals results)))

(defn get-todo
  [todo-id]
  (let [results (sql/query db-spec
                    ["SELECT * FROM locations WHERE id = ?" todo-id])]
    results))

(defn get-all-todos
  []
  (let [results (sql/query db-spec
                    ["select * from locations"])]
    results))

(defn completed-todo
  [x y]
  (let [results (sql/update! db-spec :locations
                    {:done y} ["id = ?" x])]
    (vals results)))

(defn delete-todo
  [x]
  (let [results (sql/delete! db-spec :locations ["id = ?" x])]
    (vals results)))
