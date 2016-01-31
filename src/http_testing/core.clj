(ns http-testing.core
  (:require
   [manifold.deferred :as md]
   [manifold.stream :as ns]
   [aleph.http :as http]
   [cheshire.core :as json]
   [byte-streams :as bs]
   [compojure.core :as compojure :refer [GET]]
   [compojure.route :as route])
  (:gen-class))

(defn fetch
  [uri headers]
  (let [header (merge {:headers headers})]
    (http/get uri header)))

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

(defn forbidden-handler
  [req]
  {:status 403
   :headers {"content-type" "application/json"}
   :body (json/generate-string {:code 404 :data "go away, you are forbidden"})})

(defn no-json-handler
  [req]
  {:status 200
   :headers {"content-type" "text/plain"}
   :body "hi"})

(def handler
  (compojure/routes
   (GET "/200" [] success-handler)
   (GET "/403" [] forbidden-handler)
   (GET "/404" [] not-found-handler)
   (GET "/no-json" [] no-json-handler)))


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
