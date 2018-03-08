(ns numbers-in-words.core
  (:gen-class))

(def ^:private less-than-twenty-in-words ["zero" "one" "two" "three" "four" "five" "six" "seven" "eight" "nine" "ten" "eleven" "twelve" "thirteen" "fourteen" "fifteen" "sixteen" "seventeen" "eighteen" "nineteen"])

(def ^:private tens-in-words ["twenty" "thirty" "fourty" "fifty" "sixty" "seventy" "eighty" "ninety"])

(defn- less-than-one-hundred-in-words [n]
  (let [t (unchecked-divide-int n 10)
        u (rem n 10)
        tens (if (zero? t)
               " and"
               (tens-in-words (- t 2)))
        ]
    (if (zero? u)
      tens
      (str tens " " (less-than-twenty-in-words u)))))

(defn- less-than-one-thousand-in-words [n]
  (let [hundreds (unchecked-divide-int n 100)
        tens-and-units (rem n 100)
        tens (unchecked-divide-int tens-and-units 10)]
    (str (less-than-twenty-in-words hundreds) " hundred"
         (if (zero? tens-and-units)
           ""
           (str (if (zero? tens) "" " and ") (less-than-one-hundred-in-words tens-and-units))))))

(defn- less-than-one-million-in-words [n] "one thousand and five")

(defn in-words [n]
  (cond
    (< n 20) (less-than-twenty-in-words n)
    (< n 100) (less-than-one-hundred-in-words n)
    (< n 1000) (less-than-one-thousand-in-words n)
    (< n 1000000) (less-than-one-million-in-words n)
    :else "I don't know"))

(defn- print-answer [n]
  (println (str "The answer is " (in-words n))))

(defn- print-help []
  (println "Please provide a numeric argument."))

(defn -main
  "I can print numbers in words."
  [& args]
  (if (> (count args) 0)
    (let [n (read-string (first args))]
      (if (number? n)
        (print-answer n)
        (print-help)
        ))
    (print-help)))
