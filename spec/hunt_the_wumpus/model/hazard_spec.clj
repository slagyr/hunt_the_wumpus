(ns hunt-the-wumpus.model.hazard-spec
  (:use
    [speclj.core]
    [hunt-the-wumpus.model.hazard]
    [hunt-the-wumpus.model.game :only (new-game)]
    [hunt-the-wumpus.model.player :only (place-player)]
    [hunt-the-wumpus.model.map :only (donut-map)]))

(describe "Hazards"

  (it "can place a hazard"
    (let [game (place-hazard (new-game) :wumpus 1)]
      (should= [1] (:wumpus (:hazards game)))))

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
