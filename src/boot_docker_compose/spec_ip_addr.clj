(ns boot-docker-compose.spec-ip-addr
  (:require [clojure.core :as core]
            [clojure.string :as string]
            [clojure.spec :as s]
            [clojure.spec.gen :as gen]))

;; See
;; http://blog.markhatton.co.uk/2011/03/15/regular-expressions-for-ip-addresses-cidr-ranges-and-hostnames
;; for regexes. Added fix to hostname for escaping of the dot.

(def ^:private gen-char-hex
  "Generate hex characters."
  (gen/fmap core/char
            (gen/one-of [(gen/choose 48 57)
                         (gen/choose 65 70)
                         (gen/choose 97 102)])))

(def ^:private gen-ipv4-address
  "Generate IPv4 address."
  (gen/fmap #(string/join "." %)
            (gen/vector (gen/choose 0 255) 4)))

(def ^:private gen-ipv6-address
  "Generate IPv6 address."
  ;; TODO randomly omit leading zeros
  ;; TODO randomly replace one consecutive group of zero value with two consecutive colons
  ;; TODO randomly use dotted-quad notation
  (gen/fmap #(string/join ":" (map (fn [x] (reduce str x)) %))
            (gen/vector (gen/vector gen-char-hex 4) 8)))

(def ^:private gen-ipv4-cidr
  "Generate IPv4 Classless Inter-Domain Routing."
  (gen/fmap #(string/join %)
            (gen/tuple gen-ipv4-address (gen/return "/") (gen/large-integer* {:min 0}))))

(def ^:private gen-ipv6-cidr
  "Generate IPv6 Classless Inter-Domain Routing."
  (gen/fmap #(string/join %)
            (gen/tuple gen-ipv6-address (gen/return "/") (gen/large-integer* {:min 0}))))

(def ^:private gen-hostname-part
  "Generate an internet hostname part."
  (gen/fmap #(string/join (map (fn [x] (reduce str x)) %))
            (gen/one-of [(gen/tuple (gen/vector (gen/char-alpha) 1))
                         (gen/tuple (gen/vector (gen/char-alpha) 1)
                                    (gen/vector (gen/one-of [(gen/char-alphanumeric) (gen/return \-)]))
                                    (gen/vector (gen/char-alphanumeric) 1))])))

(def ^:private gen-hostname
  "Generate an internet hostname."
  (gen/fmap #(string/join "." %)
            (gen/vector gen-hostname-part 1 2)))

(def ^:private ipv4-address-regex
  #"^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$")

(defn- ipv4-address? [x] (and (string? x) #(re-matches ipv4-address-regex %)))
(s/def ::ipv4-address (s/with-gen ipv4-address? #(gen/such-that ipv4-address? gen-ipv4-address)))

(def ^:private ipv6-address-regex
  #"^s*((([0-9A-Fa-f]{1,4}:){7}([0-9A-Fa-f]{1,4}|:))|(([0-9A-Fa-f]{1,4}:){6}(:[0-9A-Fa-f]{1,4}|((25[0-5]|2[0-4]d|1dd|[1-9]?d)(.(25[0-5]|2[0-4]d|1dd|[1-9]?d)){3})|:))|(([0-9A-Fa-f]{1,4}:){5}(((:[0-9A-Fa-f]{1,4}){1,2})|:((25[0-5]|2[0-4]d|1dd|[1-9]?d)(.(25[0-5]|2[0-4]d|1dd|[1-9]?d)){3})|:))|(([0-9A-Fa-f]{1,4}:){4}(((:[0-9A-Fa-f]{1,4}){1,3})|((:[0-9A-Fa-f]{1,4})?:((25[0-5]|2[0-4]d|1dd|[1-9]?d)(.(25[0-5]|2[0-4]d|1dd|[1-9]?d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){3}(((:[0-9A-Fa-f]{1,4}){1,4})|((:[0-9A-Fa-f]{1,4}){0,2}:((25[0-5]|2[0-4]d|1dd|[1-9]?d)(.(25[0-5]|2[0-4]d|1dd|[1-9]?d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){2}(((:[0-9A-Fa-f]{1,4}){1,5})|((:[0-9A-Fa-f]{1,4}){0,3}:((25[0-5]|2[0-4]d|1dd|[1-9]?d)(.(25[0-5]|2[0-4]d|1dd|[1-9]?d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){1}(((:[0-9A-Fa-f]{1,4}){1,6})|((:[0-9A-Fa-f]{1,4}){0,4}:((25[0-5]|2[0-4]d|1dd|[1-9]?d)(.(25[0-5]|2[0-4]d|1dd|[1-9]?d)){3}))|:))|(:(((:[0-9A-Fa-f]{1,4}){1,7})|((:[0-9A-Fa-f]{1,4}){0,5}:((25[0-5]|2[0-4]d|1dd|[1-9]?d)(.(25[0-5]|2[0-4]d|1dd|[1-9]?d)){3}))|:)))(%.+)?s*")

(defn- ipv6-address? [x] (and (string? x) #(re-matches ipv6-address-regex %)))
(s/def ::ipv6-address (s/with-gen ipv6-address? #(gen/such-that ipv6-address? gen-ipv6-address)))

(s/def ::address (s/alt :ipv4 ::ipv4-address :ipv6 ::ipv6-address))

(def ^:private ipv4-cidr-regex
  (let [x (str ipv4-address-regex)
        x (subs x 0 (dec (count x)))]
    (re-pattern (str x #"(\/([0-9]|[1-2][0-9]|3[0-2]))$"))))

(defn- ipv4-cidr? [x] (and (string? x) #(re-matches ipv4-cidr-regex %)))
(s/def ::ipv4-cidr (s/with-gen ipv4-cidr? #(gen/such-that ipv4-cidr? gen-ipv4-cidr)))

(def ^:private ipv6-cidr-regex
  (re-pattern (str ipv6-address-regex #"(\/(d|dd|1[0-1]d|12[0-8]))$")))

(defn- ipv6-cidr? [x] (and (string? x) #(re-matches ipv6-cidr-regex %)))
(s/def ::ipv6-cidr (s/with-gen ipv6-cidr? #(gen/such-that ipv6-cidr? gen-ipv6-cidr)))

(s/def ::cidr (s/alt :ipv4 ::ipv4-cidr :ipv6 ::ipv6-cidr))

(def ^:private hostname-regex
  #"^(([a-zA-Z]|[a-zA-Z][a-zA-Z0-9-]*[a-zA-Z0-9])\.)*([A-Za-z]|[A-Za-z][A-Za-z0-9-]*[A-Za-z0-9])$")

(defn- hostname? [x] (and (string? x) #(re-matches hostname-regex %)))
(s/def ::hostname (s/with-gen hostname? #(gen/such-that hostname? gen-hostname)))
