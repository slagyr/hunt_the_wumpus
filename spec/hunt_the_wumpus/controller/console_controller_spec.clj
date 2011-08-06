(ns hunt-the-wumpus.controller.console-controller-spec
  (:use
    [speclj.core]
    [joodo.spec-helpers.controller]
    [hunt-the-wumpus.controller.console-controller]))

(describe "Console Controller"
  (with-mock-rendering)
  (with-routes console-controller)

  (it "renders the console"
    (do-get "/console")
    (should= "/console/show" @rendered-template))
  )
