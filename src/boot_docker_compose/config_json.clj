(ns boot-docker-compose.config-json
  (:require [clojure.java.io :as io]
            [cheshire.core :as cheshire]
            [webjure.json-schema.validator :as validator])
  (:gen-class))

(def file-schema-v2-0
  (io/file (io/resource "docker_compose_config_schema_v2.0.json")))

(defn validate-schema-v2-0
  [data]
  (let [schema (cheshire/parse-string (slurp file-schema-v2-0))
        data (cheshire/parse-string data)]
    (validator/validate schema data)))
