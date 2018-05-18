(defproject vancouver-meetup-1205 "0.1.0-SNAPSHOT"
  :description "connecting to db and building queries with honeysql"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/java.jdbc "0.7.6"]
                 [mysql/mysql-connector-java "5.1.38"]
                 [honeysql "0.9.2"]])