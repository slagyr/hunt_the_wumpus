(ns hunt-the-wumpus.model.player-spec
  (:use
    [speclj.core]
    [hunt-the-wumpus.model.player]
    [hunt-the-wumpus.model.game :only (new-game)]
    [hunt-the-wumpus.model.game :only (new-game)]))

(describe "player"

  (it "joins the game"
    (let [game (new-game :caverns {1 {}})
          after (join-game game "Thor")]
      (should= {"Thor" {:cavern 1}} (:players after))))

  (it "moves a player to the same location"
    (let [game {:players {"player-1" {:cavern 1}}}]
      (move-player-to-location game "player-1" 1)
      (should= 1 (player-location game "player-1"))))

  (it "moves a player to a different location"
    (let [game {:players {"player-1" {:cavern 1}}}
          game (move-player-to-location game "player-1" 5)]
      (should= 5 (player-location game "player-1"))))

  (it "moves a player in a direction"
    (let [game {:players {"player-1" {:cavern 1}}
                :caverns {1 {:east 2}}}
          game (move-player-in-direction game "player-1" :east)]
      (should= 2 (player-location game "player-1"))))

  (it "moves a player in a wrong direction"
    (let [game {:players {"player-1" {:cavern 1}}
                :caverns {1 {:west 2}}}]
      (should-throw
        Exception "You can't go east from here."
        (move-player-in-direction game "player-1" :east))))

  (it "moves a player in an illegal direction"
    (let [game {:players {"player-1" {:cavern 1}}
                :caverns {1 {:west 2}}
                :messages []}]
      (should-throw (move-player-in-direction game "player-1" "E"))))

  )
