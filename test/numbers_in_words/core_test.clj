(ns numbers-in-words.core-test
  (:require [clojure.test :refer :all]
            [numbers-in-words.core :refer :all]))

(deftest lots-of-numbers-in-words
  (testing "Numbers in words"
    (doseq [[number expected-word]
            {        0 "zero"
                     1 "one"
                    21 "twenty one"
                   105 "one hundred and five"
                   123 "one hundred and twenty three"
                  1005 "one thousand and five"
                  1042 "one thousand and fourty two"
                  1105 "one thousand one hundred and five"
            ;  56945781 "fifty six million nine hundred and fourty five thousand seven hundred and eighty one"
            ; 999999999 "nine hundred and ninety nine million nine hundred and ninety nine thousand nine hundred and ninety nine"
             }]
      (testing (str number)
        (is (= expected-word (in-words number)))))))
