(ns hunt-the-wumpus.controller.login-controller-spec
  (:use
    [speclj.core]
    [joodo.spec-helpers.controller]
    [hunt-the-wumpus.controller.login-controller]
    [hunt-the-wumpus.model.game :only (perform-command)]
    [hunt-the-wumpus.model.player :only (join-game)]))

(describe "Login Controller"
  (with-mock-rendering)
  (with-routes login-controller)

  (with executed-command (atom nil))
  (around [it]
    (binding [perform-command (fn [game player thunk] (reset! @executed-command thunk))]
      (it)))

  (it "does not allow empty names"
    (let [response (do-post "/login" :params {:player-name ""})]
      (should-redirect-to response "/")))

  (it "joins game on login"
    (let [response (do-post "/login" :params {:player-name "Joe"})]
      (should-redirect-to response "/console")))

  (it "puts the player name in the session on login"
    (let [response (do-post "/login" :params {:player-name "Joe"})]
      (should= "Joe" (:player-name (:session response)))))

  (it "joins the game with the player"
    (let [response (do-post "/login" :params {:player-name "Joe"})]
      (should= join-game @@executed-command)
      (should-not= nil (:report (:session response)))))



  )