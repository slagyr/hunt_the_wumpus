(ns hunt-the-wumpus.model.report-spec
  (:use
    [speclj.core]
    [hunt-the-wumpus.model.report]
    [hunt-the-wumpus.model.game :only (new-game)]
    [hunt-the-wumpus.model.player :only (place-player)]
    [hunt-the-wumpus.model.hazard :only (place-hazard)]
    [hunt-the-wumpus.model.map :only (donut-map)]))

(describe "Reporting"

  (it "reports single possible direction"
    (let [game {:players {"Thor" {:cavern 1}}
                :caverns {1 {:west 2}}}]
      (should= ["You can go west."] (cavern-report game "Thor"))))

  (it "reports several possible directions"
    (let [game {:players {"Thor" {:cavern 1}}
                :caverns {1 {:west 2 :east 3 :north 4 :south 5}}}]
      (should= #{"You can go north." "You can go east." "You can go south." "You can go west."}
        (set (cavern-report game "Thor")))))

  (it "reports a player joining the game"
    (let [before (new-game)
          after (new-game :players {"Thor" {:cavern 1}})]
      (should= ["You have joined the game"] (player-report before after "Thor"))
      (should= ["Thor has joined the game"] (player-report before after "Edgar"))))


  (it "reports a wumpus hazard"
    (should= "You smell the Wumpus." (report-hazard :wumpus)))

  (context "with donut"

    (with game
      (->
        (new-game :caverns (donut-map))
        (place-hazard :wumpus 1)
        (place-player "Thor" 5)))

    (it "reports no detected wumpus"
      (should= [] (hazard-report @game "Thor")))

    (it "reports detected wumpus"
      (let [game (place-player @game "Thor" 2)]
        (should= ["You smell the Wumpus."] (hazard-report game "Thor"))))

    )
  )
