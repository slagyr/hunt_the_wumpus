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

  (context "reporting"

    (it "status of an empty cavern"
      (should=
        {}
        (report "zelda" (create-game :caverns {1 {}} :players {"zelda" {:cavern 1}}))))

    (it "paths from the current cavern"
      (should=
        {:paths [:north, :east, :south, :west]}
        (report "zelda"
          (create-game
            :caverns {1 {:north 2 :east 3 :south 4 :west 5}}
            :players {"zelda" {:cavern 1}}))))

    (it "arrow found"
      (should=
        {:items-found [:arrow]}
        (report "zelda"
          (create-game
            :caverns {1 {}}
            :players {"zelda" {:cavern 1}}
            :items {1 [:arrow]}))))

    (it "wumpus nearby"
      (should=
        {:paths [:north] :hazzards-detected [:wumpus]}
        (report "zelda"
          (create-game
            :caverns {1 {:north 2}}
            :players {"zelda" {:cavern 1}}
            :hazzards {:wumpus [2]}))))
    )
  )
