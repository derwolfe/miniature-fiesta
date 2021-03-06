(ns http-testing.core
  (:require
   [manifold.deferred :as md]
   [manifold.stream :as ns]
   [aleph.http :as http]
   [cheshire.core :as json]
   [byte-streams :as bs]
   [compojure.core :as compojure :refer [GET]]
   [compojure.route :as route]
   [clojure.string :as str]
   [taoensso.timbre :as timbre])
  (:gen-class))

(defn bad-log
  [msg error]
  (println (str/join " " msg error)))

(defn fetch!
  [uri headers]
  (http/get uri headers))

(defn fetch-and-do-a-thing!
  [uri headers]
  (->
   (md/chain
    (fetch! uri headers)
    :body
    bs/to-string
    #(json/parse-string % true))
   (md/catch Exception
             (fn [exc]
               (bad-log "error fetching:" (.getMessage exc))
               ::fetch-error))))

(defn fetch-consumer!
  [uri headers]
  (->
   (fetch! uri headers)
   (md/catch Exception
             (fn [exc]
               (bad-log "error fetching:" (.getMessage exc))
               ::fetch-error))))

;; test server
(defn success-handler
  [req]
  {:status 200
   :headers {"content-type" "application/json"}
   :body (json/generate-string {:code 200 :data "hi hi"})})

(defn not-found-handler
  [req]
  {:status 404
   :headers {"content-type" "application/json"}
   :body (json/generate-string {:code 404 :data "not here"})})

(defn no-json-handler
  [req]
  {:status 200
   :headers {"content-type" "text/html"}
   :body "hi"})

(def handler
  (compojure/routes
   (GET "/200" [] success-handler)
   (GET "/404" [] not-found-handler)
   (GET "/no-json" [] no-json-handler)))


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
