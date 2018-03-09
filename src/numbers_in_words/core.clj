(ns numbers-in-words.core)

(def ^:private less-than-twenty-in-words ["zero" "one" "two" "three" "four" "five" "six" "seven" "eight" "nine" "ten" "eleven" "twelve" "thirteen" "fourteen" "fifteen" "sixteen" "seventeen" "eighteen" "nineteen"])

(def ^:private tens-in-words ["twenty" "thirty" "fourty" "fifty" "sixty" "seventy" "eighty" "ninety"])

(defn- quotient-and-remainder [divisor n] [(unchecked-divide-int n divisor) (rem n divisor)])

(defn- less-than-one-hundred-in-words [n]
  (let [[number-of-tens units] (quotient-and-remainder 10 n)
        less-than-ten (zero? number-of-tens)
        tens (if less-than-ten "" (tens-in-words (- number-of-tens 2)))]
    (if (zero? units)
      tens
      (str (if less-than-ten "" (str tens " "))
           (less-than-twenty-in-words units)))))

(defn- part-in-words [quotient-name quotient-in-words divisor remainder-in-words anding and-with-quotient]
  (fn [n]
    (let [[quotient remainder] (quotient-and-remainder divisor n)
          leftover (unchecked-divide-int remainder (/ divisor 10))]
      (str (if (zero? quotient) "" (str (quotient-in-words quotient) " " quotient-name))
           (if (zero? remainder) "" (str (anding (if and-with-quotient quotient leftover)) (remainder-in-words remainder)))))))

(defn in-words [n]
  (let [and-when-zero (fn [n] (if (zero? n) " and " " "))
        and-when-not-zero (fn [n] (if (zero? n) "" " and "))
        less-than-one-thousand-in-words (part-in-words "hundred" less-than-twenty-in-words 100 less-than-one-hundred-in-words and-when-not-zero true)
        less-than-one-million-in-words (part-in-words "thousand" less-than-one-thousand-in-words 1000 less-than-one-thousand-in-words and-when-zero false)
        less-than-one-trillion-in-words (part-in-words "million" less-than-one-thousand-in-words 1000000 less-than-one-million-in-words and-when-zero false)]
    (cond
      (< n 20) (less-than-twenty-in-words n)
      (< n 100) (less-than-one-hundred-in-words n)
      (< n 1000) (less-than-one-thousand-in-words n)
      (< n 1000000) (less-than-one-million-in-words n)
      (< n 1000000000) (less-than-one-trillion-in-words n)
      :else "I don't know")))
