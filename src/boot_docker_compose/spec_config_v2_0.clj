(ns boot-docker-compose.spec-config-v2-0
  (:require [clojure.string :as string]
            [clojure.spec :as s]
            [clojure.spec.gen :as gen]
            [boot-docker-compose.spec-ip-addr]))

(defn- boolean? [x] (instance? Boolean x))
(s/def ::boolean (s/with-gen boolean? #(gen/boolean)))


;;  "definitions": {

;;      "service": {
;;          "id": "#/definitions/service",
;;          "type": "object",
;;          "properties": {
;;              "build": {
;;                  "oneOf": [
;;                      {"type": "string"},
;;                      {
;;                          "type": "object",
;;                          "properties": {
;;                              "context": {"type": "string"},
;;                              "dockerfile": {"type": "string"},
;;                              "args": {"$ref": "#/definitions/list_or_dict"}
;;                          },
;;                          "additionalProperties": false
;;                      }
;;                  ]
;;              },
;;              "cap_add": {"type": "array", "items": {"type": "string"}, "uniqueItems": true},
;;              "cap_drop": {"type": "array", "items": {"type": "string"}, "uniqueItems": true},
;;              "cgroup_parent": {"type": "string"},
;;              "command": {
;;                  "oneOf": [
;;                      {"type": "string"},
;;                      {"type": "array", "items": {"type": "string"}}
;;                  ]
;;              },
;;              "container_name": {"type": "string"},
;;              "cpu_shares": {"type": ["number", "string"]},
;;              "cpu_quota": {"type": ["number", "string"]},
;;              "cpuset": {"type": "string"},
;;              "depends_on": {"$ref": "#/definitions/list_of_strings"},
;;              "devices": {"type": "array", "items": {"type": "string"}, "uniqueItems": true},
;;              "dns": {"$ref": "#/definitions/string_or_list"},
;;              "dns_search": {"$ref": "#/definitions/string_or_list"},
;;              "domainname": {"type": "string"},
;;              "entrypoint": {
;;                  "oneOf": [
;;                      {"type": "string"},
;;                      {"type": "array", "items": {"type": "string"}}
;;                  ]
;;              },
;;              "env_file": {"$ref": "#/definitions/string_or_list"},
;;              "environment": {"$ref": "#/definitions/list_or_dict"},
;;
;;              "expose": {
;;                  "type": "array",
;;                  "items": {
;;                      "type": ["string", "number"],
;;                      "format": "expose"
;;                  },
;;                  "uniqueItems": true
;;              },
;;
;;              "extends": {
;;                  "oneOf": [
;;                      {
;;                          "type": "string"
;;                      },
;;                      {
;;                          "type": "object",
;;
;;                          "properties": {
;;                              "service": {"type": "string"},
;;                              "file": {"type": "string"}
;;                          },
;;                          "required": ["service"],
;;                          "additionalProperties": false
;;                      }
;;                  ]
;;              },
;;
;;              "external_links": {"type": "array", "items": {"type": "string"}, "uniqueItems": true},
;;              "extra_hosts": {"$ref": "#/definitions/list_or_dict"},
;;              "hostname": {"type": "string"},
;;              "image": {"type": "string"},
;;              "ipc": {"type": "string"},
;;              "labels": {"$ref": "#/definitions/list_or_dict"},
;;              "links": {"type": "array", "items": {"type": "string"}, "uniqueItems": true},
;;
;;              "logging": {
;;                  "type": "object",
;;
;;                  "properties": {
;;                      "driver": {"type": "string"},
;;                      "options": {"type": "object"}
;;                  },
;;                  "additionalProperties": false
;;              },
;;
;;              "mac_address": {"type": "string"},
;;              "mem_limit": {"type": ["number", "string"]},
;;              "memswap_limit": {"type": ["number", "string"]},
;;              "network_mode": {"type": "string"},
;;
;;              "networks": {
;;                  "oneOf": [
;;                      {"$ref": "#/definitions/list_of_strings"},
;;                      {
;;                          "type": "object",
;;                          "patternProperties": {
;;                              "^[a-zA-Z0-9._-]+$": {
;;                                  "oneOf": [
;;                                      {
;;                                          "type": "object",
;;                                          "properties": {
;;                                              "aliases": {"$ref": "#/definitions/list_of_strings"},
;;                                              "ipv4_address": {"type": "string"},
;;                                              "ipv6_address": {"type": "string"}
;;                                          },
;;                                          "additionalProperties": false
;;                                      },
;;                                      {"type": "null"}
;;                                  ]
;;                              }
;;                          },
;;                          "additionalProperties": false
;;                      }
;;                  ]
;;              },
;;              "pid": {"type": ["string", "null"]},
;;
;;              "ports": {
;;                  "type": "array",
;;                  "items": {
;;                      "type": ["string", "number"],
;;                      "format": "ports"
;;                  },
;;                  "uniqueItems": true
;;              },
;;
;;              "privileged": {"type": "boolean"},
;;              "read_only": {"type": "boolean"},
;;              "restart": {"type": "string"},
;;              "security_opt": {"type": "array", "items": {"type": "string"}, "uniqueItems": true},
;;              "shm_size": {"type": ["number", "string"]},
;;              "stdin_open": {"type": "boolean"},
;;              "stop_signal": {"type": "string"},
;;              "tmpfs": {"$ref": "#/definitions/string_or_list"},
;;              "tty": {"type": "boolean"},
;;              "ulimits": {
;;                  "type": "object",
;;                  "patternProperties": {
;;                      "^[a-z]+$": {
;;                          "oneOf": [
;;                              {"type": "integer"},
;;                              {
;;                                  "type":"object",
;;                                  "properties": {
;;                                      "hard": {"type": "integer"},
;;                                      "soft": {"type": "integer"}
;;                                  },
;;                                  "required": ["soft", "hard"],
;;                                  "additionalProperties": false
;;                              }
;;                          ]
;;                      }
;;                  }
;;              },
;;              "user": {"type": "string"},
;;              "volumes": {"type": "array", "items": {"type": "string"}, "uniqueItems": true},
;;              "volume_driver": {"type": "string"},
;;              "volumes_from": {"type": "array", "items": {"type": "string"}, "uniqueItems": true},
;;              "working_dir": {"type": "string"}
;;          },
;;
;;          "dependencies": {
;;              "memswap_limit": ["mem_limit"]
;;          },
;;          "additionalProperties": false
;;      },

(s/def ::service (s/keys :opt []))

(s/def ::subnet :boot-docker-compose.spec-ip-addr/cidr)
(s/def ::ip_range :boot-docker-compose.spec-ip-addr/cidr)
(s/def ::gateway :boot-docker-compose.spec-ip-addr/address)
(s/def ::aux_addresses (s/map-of :boot-docker-compose.spec-ip-addr/hostname :boot-docker-compose.spec-ip-addr/address))
(s/def ::config (s/* (s/keys :opt [::subnet ::ip_range ::gateway ::aux_addresses])))

(s/def ::ipam (s/keys :opt [::driver ::config]))

(s/def ::driver string?)

(def ^:private gen-driver_opts-key
  "Generate a config key."
  (gen/fmap keyword
            (gen/string-ascii)))

(defn- driver_opts-key? [x] (and (keyword? x) #(re-matches #"^.+$" (name %))))
(s/def ::driver_opts-key (s/with-gen driver_opts-key? #(gen/such-that driver_opts-key? gen-driver_opts-key)))

(s/def ::driver_opts-key (s/and keyword? #(re-matches #"^.+$" (name %))))
(s/def ::driver_opts-val (s/alt :string string? :number number?))
(s/def ::driver_opts (s/map-of ::driver_opts-key ::driver_opts-val))

(s/def ::name string?)
(s/def ::external (s/alt :boolean ::boolean :object (s/keys :req [::name])))

(s/def ::network (s/keys :opt [::ipam ::driver ::driver_opts ::external]))

(s/def ::volume (s/keys :opt [::driver ::driver_opts ::external]))

;;      "string_or_list": {
;;          "oneOf": [
;;              {"type": "string"},
;;              {"$ref": "#/definitions/list_of_strings"}
;;          ]
;;      },

;;      "list_of_strings": {
;;          "type": "array",
;;          "items": {"type": "string"},
;;          "uniqueItems": true
;;      },

;;      "list_or_dict": {
;;          "oneOf": [
;;              {
;;                  "type": "object",
;;                  "patternProperties": {
;;                      ".+": {
;;                          "type": ["string", "number", "null"]
;;                      }
;;                  },
;;                  "additionalProperties": false
;;              },
;;              {"type": "array", "items": {"type": "string"}, "uniqueItems": true}
;;          ]
;;      },

;;      "constraints": {
;;          "service": {
;;              "id": "#/definitions/constraints/service",
;;              "anyOf": [
;;                  {"required": ["build"]},
;;                  {"required": ["image"]}
;;              ],
;;              "properties": {
;;                  "build": {
;;                      "required": ["context"]
;;                  }
;;              }
;;          }
;;      }
;;   }
;; }

(s/def ::version string?)

(def ^:private gen-config-key
  "Generate a config key."
  (gen/fmap #(keyword (string/join %))
            (gen/vector (gen/one-of [(gen/char-alphanumeric)
                                     (gen/return \.) (gen/return \_) (gen/return \-)]))))

(defn- config-key? [x] (and (keyword? x) #(re-matches #"^[a-zA-Z0-9._-]+$" (name %))))
(s/def ::config-key (s/with-gen config-key? #(gen/such-that config-key? gen-config-key)))

(s/def ::services (s/map-of ::config-key ::service))
(s/def ::networks (s/map-of ::config-key ::network))
(s/def ::volumes (s/map-of ::config-key ::volume))

(s/def ::spec-config (s/keys :opt [::version ::services ::networks ::volumes]))
