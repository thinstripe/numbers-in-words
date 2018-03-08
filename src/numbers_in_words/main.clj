(ns numbers-in-words.main
  (:require [numbers-in-words.core :as numbers :only [in-words]])
  (:gen-class))

(defn- print-answer [n]
  (println (str "The answer is " (numbers/in-words n))))

(defn- print-help []
  (println "Please provide a numeric argument."))

(defn -main
  "I can print numbers in words."
  [& args]
  (if (> (count args) 0)
    (let [n (read-string (first args))]
      (if (number? n)
        (print-answer n)
        (print-help)))
    (print-help)))
