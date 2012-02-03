(ns dom-css.repl
  "dom-css repl helpers"
  {:author "Naitik Shah"}
  (:require
    [clojure.string]
    [clojure.tools.logging])
  (:use
    [dom-css.core]
    [auto-reload.core :only [auto-reload]]))

(auto-reload ["src"])
