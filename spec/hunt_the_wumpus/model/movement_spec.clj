(ns hunt-the-wumpus.model.movement-spec
  (:use
    [speclj.core]
    [hunt-the-wumpus.model.movement]))

(describe "Movement"
  (it "moves a player to the same location"
    (let [game {:players (ref {"player-1" {:cavern 1}})}]
      (move-player-to-location! game "player-1" 1)
      (should= 1 (player-location game "player-1"))))

  (it "moves a player to a different location"
    (let [game {:players (ref {"player-1" {:cavern 1}})}]
      (move-player-to-location! game "player-1" 5)
      (should= 5 (player-location game "player-1"))))

  (it "moves a player in a direction"
    (let [game {:players (ref {"player-1" {:cavern 1}})
                :caverns (ref {1 {:east 2}})}]
      (move-player-in-direction! game "player-1" :east)
      (should= 2 (player-location game "player-1"))))

  (it "moves a player in a wrong direction"
    (let [game {:players (ref {"player-1" {:cavern 1}})
                :caverns (ref {1 {:west 2}})}
          report (move-player-in-direction! game "player-1" :east)]
      (should= 1 (player-location game "player-1"))
      (should= {:error "You can't go east from here."} report)))

  (it "moves a player in an illegal direction"
    (let [game {:players (ref {"player-1" {:cavern 1}})
                :caverns (ref {1 {:west 2}})
                :messages (ref [])}]
      (should-throw (move-player-in-direction! game "player-1" "E"))))

  (it "gets a single possible direction"
    (let [game {:players (ref {"player-1" {:cavern 1}})
                :caverns (ref {1 {:west 2}})}]
      (should= [:west] (possible-directions game "player-1"))))

  (it "gets several possible directions"
    (let [game {:players (ref {"player-1" {:cavern 1}})
                :caverns (ref {1 {:west 2 :east 3 :north 4 :south 5}})}]
      (should= (set [:south :east :north :west])
               (set (possible-directions game "player-1")))))


)

