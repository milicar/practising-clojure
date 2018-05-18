(ns vancouver-meetup-1205.core
  (:require [clojure.java.jdbc :as jdbc]
            [honeysql.core :as sql]
            [honeysql.helpers :as helpers]))

; mysql specs
(def mysql-db {:dbtype "mysql"
               :dbname "dms"
               :user "user"
               :password "pass"})

; plain jdbc query and insert
(jdbc/insert! mysql-db :document {:document_location "/clj" :document_name "Clojure" :author_id 3 :document_type_id 4})
(jdbc/query mysql-db ["select * from document where author_id = ?" 3])

; query as a map, and reformatting with honeysql
(def all-docs {:select [:document_name]
               :from [:document]})
(def all-docs-query (sql/format all-docs))

(jdbc/query mysql-db all-docs-query)

; build a query
(jdbc/query mysql-db
            (sql/format
              (sql/build :select :*
                         :from :document
                         :where [:= :author_id "3"]
                         :limit 3
                         :offset 1)))

; execute query provided as map
(defn execute-query [sql-map]
  (->> sql-map
       (sql/format)
       (jdbc/query mysql-db)))

(execute-query all-docs)

; build query with offset and limit provided or default
(defn build-query-with-pagination [sql-map {:keys [offset limit]
                                              :or {offset 0 limit 10}}]
  (sql/build sql-map :offset offset :limit limit))

(execute-query
  (build-query-with-pagination all-docs {:limit 3 :offset 4}))
; default arguments, but still a map has to be provided
(execute-query
  (build-query-with-pagination all-docs {}))

; build query with optional offset and limit arguments
(defn build-query-with-pagination-2 [sql-map & {:keys [offset limit]
                                                :or {offset 1 limit 3}}]
  (sql/build sql-map :offset offset :limit limit))

; no empty map needs to be provided
(execute-query
  (build-query-with-pagination-2 all-docs))

