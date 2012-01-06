(ns dom-css.test.core
  "Test dom-css functionality."
  {:author "Naitik Shah"}
  (:use
    [dom-css.core :only [inline extract]]
    [clojure.test :only [deftest testing is]]))

(deftest extract-test-single
  (let [input [[:a {:css :foo}]]
        [css [[tag attrs content]]] (extract input)]
    (is (= css [:foo]))
    (is (= "a" tag))
    (is (= nil (:css attrs)))))

(deftest extract-test-multiple
  (let [input [[:a {:css :foo}] [:b {:css :bar}]]
        [css [[tag attrs content]]] (extract input)]
    (is (= css [:foo :bar]))))

(deftest extract-test-multiple-duplicate
  (let [input [[:a {:css :foo}] [:b {:css :foo}]]
        [css [[tag attrs content]]] (extract input)]
    (is (= css [:foo :foo]))))
