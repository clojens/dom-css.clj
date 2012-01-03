(ns dom-css.test.core
  "Test dom-css functionality."
  {:author "Naitik Shah"}
  (:use
    [dom-css.core :only [inline extract]]
    [clojure.test :only [deftest testing is]]))

(deftest extract-test
  (let [input [[:a {:css :foo}]]
        [css [[tag attrs content]]] (extract input)]
    (is (= css [:foo]))
    (is (= "a" tag))
    (is (= nil (:css attrs)))))
