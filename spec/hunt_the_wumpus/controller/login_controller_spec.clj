(ns hunt-the-wumpus.controller.login-controller-spec
  (:use
    [speclj.core]
    [joodo.spec-helpers.controller]
    [hunt-the-wumpus.controller.login-controller]))

(describe "Login Controller"
  (with-mock-rendering)
  (with-routes login-controller)

  (it "does not allow empty names"
    (let [response (do-post "/login" :params {:player-name ""})]
      (should-redirect-to response "/")))

  (it "joins game on login"
    (let [response (do-post "/login" :params {:player-name "Joe"})]
      (should-redirect-to response "/console")))

  (it "puts the player name in the session on login"
    (let [response (do-post "/login" :params {:player-name "Joe"})]
      (should= "Joe" (:player-name (:session response)))))

  )