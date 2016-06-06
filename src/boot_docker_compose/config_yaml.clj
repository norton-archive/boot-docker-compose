(ns boot-docker-compose.config-yaml
  (:require [clojure.java.io :as io]
            [clojure.walk :as walk]
            [clj-yaml.core :as yaml]
            [me.raynes.conch :as sh])
  (:gen-class))

(defn validate
  [x]
  (let [y (walk/stringify-keys x)
        z (yaml/generate-string y)
        temp-file (java.io.File/createTempFile "docker-compose" ".yaml")]
    (try
      (spit temp-file z :append true)
      (sh/with-programs [docker-compose]
        (docker-compose "-f" temp-file "config")
        true)
      (catch clojure.lang.ExceptionInfo e
        (println "caught" (ex-data e))
        false)
      (finally (.delete temp-file)))))
