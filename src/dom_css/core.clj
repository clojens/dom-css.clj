(ns dom-css.core
  "Extract and inline CSS from hiccup data. This allows for style data to live
  close to the markup itself, and works especially well with
  [cssgen](https://github.com/paraseba/cssgen).

  It is easiest to download the source and experiment using the repl:

    git clone https://github.com/nshah/dom-css.clj
    cd dom-css.clj
    lein repl

  A simple example that works with plain CSS strings:

    (def btn \".btn { font: bold; }\")
    (dom-css.core/inline [[:a.btn {:css btn} \"Submit\"]])

  Returns:

    ([:style \".btn{font:bold}\"]
     [\"a\" {:id nil, :class \"btn\"} (\"Submit\")])

  The underlying ability to \"extract\" all the `:css` is also available, and
  could for example be used to provide dynamically generated stylesheets."
  {:author "Naitik Shah"}
  (:require
    [clojure.string]
    [clojure.tools.logging])
  (:use
    [hdom.core :only [reduce-elements]]))

(defn minify
  "Minifies a given CSS string eliminating whitespace and optional syntax."
  [css]
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

(defn- reducer
  "Reducer to collect and remove :css attributes."
  [collected [tag attrs content :as el]]
  (if-let [css (:css attrs)]
    [(conj collected css) [tag (dissoc attrs :css) content]]
    [collected el]))

(defn extract
  "Extracts and removes the CSS attributes from the given hiccup DOM
  data. Returns the CSS and the new DOM data."
  [body]
  (reduce-elements [] body reducer))

(defn inline
  "Inlines and removes the CSS attributes from the given hiccup DOM data.
  Returns new DOM data including an added :style tag with the inlined CSS."
  [body]
  (let [[css new-body] (extract body)]
    (cons [:style (minify (apply str css))] new-body)))
