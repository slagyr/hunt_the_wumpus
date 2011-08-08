(ns hunt-the-wumpus.controller.console-controller-spec
  (:use
    [speclj.core]
    [joodo.spec-helpers.controller]
    [hunt-the-wumpus.controller.console-controller]
    [hunt-the-wumpus.model.game :only (*game* report)]))

(describe "Console Controller"
  (with-mock-rendering)
  (with-routes console-controller)

  (it "renders the console"
    (do-get "/console")
    (should= "/console/show" @rendered-template))

  (it "generates a report"
    (do-get "/console" :session {:player-name "Thor" :report {:foo :bar}})
    (should= {:foo :bar} (:report @rendered-context)))

  )
