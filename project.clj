(defproject my-todoapp "0.1.0"
  :description "TODO app with database"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [hiccup "1.0.2"]
                 [org.clojure/java.jdbc "0.7.0-alpha1"]
                 [com.h2database/h2 "1.3.170"]
                 [compojure "1.5.1"]
                 [ring/ring-defaults "0.2.1"]]
  :plugins [[lein-ring "0.9.7"]]
  :ring {:handler my-todoapp.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.0"]]}})
