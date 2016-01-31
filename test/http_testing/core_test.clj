(ns http-testing.core-test
  (:require [clojure.test :refer :all]
            [http-testing.core :as core]
            [clojure.string :as str]
            [aleph.http :as http]))

;; use this port for all requests
(def port 8080)
(def base-uri (str "http://localhost:" port))

;; start a server for tests
(http/start-server core/handler {:port port})

(deftest fetch-test
  (testing "logs error"
    @(core/fetch (str/join "/" [base-uri "404"]) {})))
