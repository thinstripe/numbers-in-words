(ns numbers-in-words.core)

(def ^:private less-than-twenty-in-words ["zero" "one" "two" "three" "four" "five" "six" "seven" "eight" "nine" "ten" "eleven" "twelve" "thirteen" "fourteen" "fifteen" "sixteen" "seventeen" "eighteen" "nineteen"])

(def ^:private tens-in-words ["twenty" "thirty" "fourty" "fifty" "sixty" "seventy" "eighty" "ninety"])

(defn- less-than-one-hundred-in-words [n]
  (let [number-of-tens (unchecked-divide-int n 10)
        units (rem n 10)
        tens (if (zero? number-of-tens)
               ""
               (tens-in-words (- number-of-tens 2)))]
    (if (zero? units)
      tens
      (str (if (zero? number-of-tens)
             ""
             (str tens " "))
           (less-than-twenty-in-words units)))))

(defn- less-than-one-thousand-in-words [n]
  (let [hundreds (unchecked-divide-int n 100)
        tens-and-units (rem n 100)
        tens (unchecked-divide-int tens-and-units 10)]
    (str (if (zero? hundreds)
           ""
           (str (less-than-twenty-in-words hundreds) " hundred"))
         (if (zero? tens-and-units)
           ""
           (str (if (zero? hundreds)
                  ""
                  " and ")
                (less-than-one-hundred-in-words tens-and-units))))))

(defn- part-in-words [quotient-name quotient-fn divisor remainder-fn]
  (fn [n]
    (let [quotient (unchecked-divide-int n divisor)
          remainder (rem n divisor)
          left-over (unchecked-divide-int remainder (/ divisor 10))]
      (str (quotient-fn quotient) " " quotient-name
           (if (zero? remainder)
             ""
             (str (if (zero? left-over)
                    " and "
                    " ")
                  (remainder-fn remainder)))))))

(defn in-words [n]
  (let [less-than-one-million-in-words (part-in-words "thousand" less-than-one-thousand-in-words 1000 less-than-one-thousand-in-words)
        less-than-one-trillion-in-words (part-in-words "million" less-than-one-thousand-in-words 1000000 less-than-one-million-in-words)]
    (cond
      (< n 20) (less-than-twenty-in-words n)
      (< n 100) (less-than-one-hundred-in-words n)
      (< n 1000) (less-than-one-thousand-in-words n)
      (< n 1000000) (less-than-one-million-in-words n)
      (< n 1000000000) (less-than-one-trillion-in-words n)
      :else "I don't know")))
