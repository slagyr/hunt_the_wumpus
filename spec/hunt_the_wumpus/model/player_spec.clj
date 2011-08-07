(ns hunt-the-wumpus.model.player-spec
  (:use
    [speclj.core]
    [hunt-the-wumpus.model.player]
    [hunt-the-wumpus.model.game :only (new-game)]))

(describe "player"

  (it "joins the game"
    (let [game (new-game :caverns {1 {}})
          after (join-game game "Thor")]
      (should= {"Thor" {:cavern 1}} (:players after))))

  (it "acknowledges a player joining the game"
    (let [before (new-game)
          after (new-game :players {"Thor" {:cavern 1}})]
      (should= ["You have joined the game"] (player-report before after "Thor"))
      (should= ["Thor has joined the game"] (player-report before after "Edgar"))))

  )
