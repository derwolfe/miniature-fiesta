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

(deftest fetch-consumer!-test
  (testing "logs error"
    (let [log (atom "")
          log-error (fn [error rest]
                      (reset! log (str/join " " [error rest])))]
      (with-redefs [core/bad-log log-error]
        (let [result (core/fetch-consumer! (str/join "/" [base-uri "404"]) {})]
          (is (identical? :http-testing.core/fetch-error result)
              "returns a fetch-error keyword")
          (is (= "error fetching: status: 404" @log)))))))

(deftest fetch-and-do-a-thing!-test
  (testing "logs error"
    (let [log (atom "")
          log-error (fn [error rest]
                      (reset! log (str/join " " [error rest])))]
      (with-redefs [core/bad-log log-error]
        (let [result (core/fetch-and-do-a-thing! (str/join "/" [base-uri "404"]) {})]
          (is (identical? :http-testing.core/fetch-error result)
              "returns a fetch-error keyword")
          (is (= "error fetching: status: 404" @log)))))))
