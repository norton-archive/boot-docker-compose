(ns boot-docker-compose.spec-ip-addr-test
  (:require [clojure.spec :as s]
            [clojure.spec.gen :as gen]
            [clojure.test.check.clojure-test :refer :all]
            [clojure.test.check.properties :as prop]
            [boot-docker-compose.spec-ip-addr :refer :all]))

(defspec address-valid? 10000
  (prop/for-all [x (s/gen :boot-docker-compose.spec-ip-addr/address)]
    (s/valid? :boot-docker-compose.spec-ip-addr/address x)))

(defspec cidr-valid? 10000
  (prop/for-all [x (s/gen :boot-docker-compose.spec-ip-addr/cidr)]
    (s/valid? :boot-docker-compose.spec-ip-addr/cidr x)))

(defspec hostname-valid? 10000
  (prop/for-all [x (s/gen :boot-docker-compose.spec-ip-addr/hostname)]
    (s/valid? :boot-docker-compose.spec-ip-addr/hostname x)))
