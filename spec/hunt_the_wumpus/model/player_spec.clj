(ns hunt-the-wumpus.model.player-spec
  (:use
    [speclj.core]
    [hunt-the-wumpus.model.player]
    [hunt-the-wumpus.model.game :only (new-game players)]))

(describe "player"

  (it "joins the game"
    (let [game (new-game :caverns {1 {}})
          report (join-game! game "Thor")]
      (should= "You have joined the game" (:ack report))
      (should= {"Thor" {:cavern 1}} (players game))))


  )
