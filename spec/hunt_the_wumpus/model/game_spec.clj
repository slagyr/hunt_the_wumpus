(ns hunt-the-wumpus.model.game-spec
  (:use
    [speclj.core]
    [hunt-the-wumpus.model.game]))

(describe "Game"

  (it "creating an empty game"
    (let [game (create-game)]
      (should= {} (caverns game))
      (should= {} (hazzards game))
      (should= {} (items game))
      (should= {} (players game))))

  (it "creates an populated game"
    (let [game (create-game :caverns {1 {}} :hazzards {:pits [1]} :items {:arrows [1]} :players {"joe" {}})]
      (should= {1 {}} (caverns game))
      (should= {:pits [1]} (hazzards game))
      (should= {:arrows [1]} (items game))
      (should= {"joe" {}} (players game))))

  (it "reports the status of an empty cavern"
    (let [game (create-game :caverns {1 {}} :players {"zelda" {:cavern 1}})]
      (should= [] (report game "zelda"))))

  )
