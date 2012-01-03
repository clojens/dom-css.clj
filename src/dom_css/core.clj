(ns dom-css.core
  "Inline CSS from hiccup data."
  {:author "Naitik Shah"}
  (:require
    [clojure.string]
    [clojure.tools.logging])
  (:use
    [hdom.core :only [reduce-elements]]))

(defn minify [css]
  (-> css
    (clojure.string/replace #"[\n|\r]" "")
    (clojure.string/replace #"/\*.*?\*/" "")
    (clojure.string/replace #"\s+" " ")
    (clojure.string/replace #"\s*:\s*" ":")
    (clojure.string/replace #"\s*,\s*" ",")
    (clojure.string/replace #"\s*\{\s*" "{")
    (clojure.string/replace #"\s*}\s*" "}")
    (clojure.string/replace #"\s*;\s*" ";")
    (clojure.string/replace #";}" "}")))

(defn- reducer [collected [tag attrs content :as el]]
  (if-let [css (:css attrs)]
    [(conj collected css) [tag (dissoc attrs :css) content]]
    [collected el]))

(defn extract [body]
  (reduce-elements [] body reducer))

(defn inline [body]
  (let [[css new-body] (extract body)]
    (cons [:style (minify (apply str css))] new-body)))
