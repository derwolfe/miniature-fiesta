(defproject http-testing "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "MIT License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [com.taoensso/timbre "4.2.0"]
                 [compojure "1.4.0"]
                 [manifold "0.1.2-alpha3"]
                 [aleph "0.4.1-beta2"]
                 [environ "1.0.1"]
                 [cheshire "5.5.0"]]
  :main ^:skip-aot http-testing.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
