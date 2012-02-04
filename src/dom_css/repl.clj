(ns dom-css.repl
  "repl helpers"
  {:author "Naitik Shah"}
  (:require
    [auto-reload.core :only [auto-reload]]
    [clojure.string]
    [clojure.tools.logging])
  (:use
    [dom-css.core]))

(auto-reload.core/auto-reload ["src"])
