(ns hunt-the-wumpus.controller.command-controller-spec
  (:use
    [speclj.core]
    [joodo.spec-helpers.controller]
    [hunt-the-wumpus.controller.command-controller]
    [hunt-the-wumpus.model.game :only (*game* perform-command)]
    [hunt-the-wumpus.model.player :only (go-north go-south go-east go-west)]))

(describe "Command Controller"
  (with-mock-rendering)
  (with-routes command-controller)
  (with executed-command (atom nil))
  (around [it]
    (binding [perform-command (fn [game player thunk] (reset! @executed-command thunk))]
      (it)))

  (it "goes north"
    (let [response (do-get "/command/move/north" :session {:player-name "Thor"})]
      (should= go-north @@executed-command)
      (should-not= nil (:report (:session response)))
      (should-redirect-to response "/console")))

  (it "goes south"
    (let [response (do-get "/command/move/south" :session {:player-name "Thor"})]
      (should= go-south @@executed-command)))

  (it "goes east"
    (let [response (do-get "/command/move/east" :session {:player-name "Thor"})]
      (should= go-east @@executed-command)))

  (it "goes west"
    (let [response (do-get "/command/move/west" :session {:player-name "Thor"})]
      (should= go-west @@executed-command)))

  (it "doesn't know how to shoot yet"
    (let [response (do-get "/command/shoot/west" :session {:player-name "Thor"})]
      (should= {:error "I don't know how to shoot."} (:report (:session response)))
      (should-redirect-to response "/console")))

  )