(def project 'boot-docker-compose)
(def version "0.1.0-SNAPSHOT")

(set-env! :resource-paths #{"resources" "src"}
          :source-paths   #{"test"}
          :dependencies   '[[org.clojure/clojure "RELEASE"]
                            [circleci/clj-yaml "0.5.5"]
                            [me.raynes/conch "0.8.0"]
                            [org.clojure/test.check "0.9.0" :scope "test"]
                            [tolitius/boot-check "0.1.2-SNAPSHOT" :scope "test"]
                            [adzerk/boot-test "RELEASE" :scope "test"]])

(task-options!
 aot {:namespace   #{'boot-docker-compose.core}}
 pom {:project     project
      :version     version
      :description "FIXME: write description"
      :url         "http://example/FIXME"
      :scm         {:url "https://github.com/norton/boot-docker-compose"}
      :license     {"The MIT License (MIT)"
                    "https//opensource.org/licenses/MIT"}}
 jar {:main        'boot-docker-compose.core
      :file        (str "boot-docker-compose-" version "-standalone.jar")})

(require '[tolitius.boot-check :as check])

(deftask check-sources
  "Check the project sources."
  []
  (set-env! :source-paths #{"src" "test"})
  (comp
   (check/with-yagni)
   (check/with-eastwood)
   (check/with-kibit)
   #_(check/with-bikeshed))) ; TODO max-line-length is configurable?

(deftask build
  "Build the project locally as a JAR."
  [d dir PATH #{str} "the set of directories to write to (target)."]
  (let [dir (if (seq dir) dir #{"target"})]
    (comp (aot) (pom) (uber) (jar) (target :dir dir))))

(deftask run
  "Run the project."
  [a args ARG [str] "the arguments for the application."]
  (require '[boot-docker-compose.core :as app])
  (apply (resolve 'app/-main) args))

(require '[adzerk.boot-test :refer [test]])
