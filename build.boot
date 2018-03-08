(def project 'numbers-in-words)
(def version "0.1.0-SNAPSHOT")

(set-env! :resource-paths #{"resources" "src"}
          :source-paths   #{"test"}
          :dependencies   '[[org.clojure/clojure "RELEASE"]
                            [adzerk/boot-test "RELEASE" :scope "test"]])

(task-options!
 aot {:namespace   #{'numbers-in-words.main}}
 pom {:project     project
      :version     version
      :description "Numbers in words"
      :scm         {:url "https://github.com/thinstripe/numbers-in-words"}
      :license     {"Eclipse Public License"
                    "http://www.eclipse.org/legal/epl-v10.html"}}
 jar {:main        'numbers-in-words.main
      :file        (str "numbers-in-words-" version "-standalone.jar")})

(deftask build
  "Build the project locally as a JAR."
  [d dir PATH #{str} "the set of directories to write to (target)."]
  (let [dir (if (seq dir) dir #{"target"})]
    (comp (aot) (pom) (uber) (jar) (target :dir dir))))

(deftask run
  "Run the project."
  [a args ARG [str] "the arguments for the application."]
  (require '[numbers-in-words.main :as app])
  (apply (resolve 'app/-main) args))

(require '[adzerk.boot-test :refer [test]])
