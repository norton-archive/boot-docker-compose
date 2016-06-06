(ns boot-docker-compose.config-yaml-test
  (:require [clojure.spec :as s]
            [clojure.spec.gen :as gen]
            [clojure.test.check.clojure-test :refer :all]
            [clojure.test.check.properties :as prop]
            [boot-docker-compose.spec-config-v2-0 :refer :all]
            [boot-docker-compose.config-yaml :as config-yaml]))

(defspec spec-config-valid? 100 ; TODO where is the bottleneck?
  (prop/for-all [x (s/gen :boot-docker-compose.spec-config-v2-0/spec-config)]
    (and (s/valid? :boot-docker-compose.spec-config-v2-0/spec-config x)
         (config-yaml/validate x))))
