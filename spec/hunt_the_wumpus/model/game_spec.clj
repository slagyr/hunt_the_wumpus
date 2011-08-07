(ns hunt-the-wumpus.model.game-spec
  (:use
    [speclj.core]
    [hunt-the-wumpus.model.game]))

(describe "Game"

  (it "creating an empty game"
    (let [game (new-game)]
      (should= {} (:caverns game))
      (should= {} (:hazards game))
      (should= {} (:items game))
      (should= {} (:players game))))

  (it "creates a populated game"
    (let [game (new-game :caverns {1 {}} :hazards {:pits [1]} :items {:arrows [1]} :players {"joe" {}})]
      (should= {1 {}} (:caverns game))
      (should= {:pits [1]} (:hazards game))
      (should= {:arrows [1]} (:items game))
      (should= {"joe" {}} (:players game))))

  (it "performs a command"
    (let [game-ref (ref (new-game :caverns {1 {:east 2}} :players {"player-1" {:cavern 1}}))
          report (perform-command game-ref "player-1" (fn [game player] (assoc game :foo player)))]
      (should= "player-1" (:foo @game-ref))
      (should= [:east] (:possible-directions report))))

  ;  (context "reporting"
  ;
  ;    (it "status of an empty cavern"
  ;      (should=
  ;        {}
  ;        (report "zelda" (new-game :caverns {1 {}} :players {"zelda" {:cavern 1}}))))
  ;
  ;    (it "paths from the current cavern"
  ;      (should=
  ;        {:paths [:north, :east, :south, :west]}
  ;        (report "zelda"
  ;          (new-game
  ;            :caverns {1 {:north 2 :east 3 :south 4 :west 5}}
  ;            :players {"zelda" {:cavern 1}}))))
  ;
  ;    (it "arrow found"
  ;      (should=
  ;        {:items-found [:arrow]}
  ;        (report "zelda"
  ;          (new-game
  ;            :caverns {1 {}}
  ;            :players {"zelda" {:cavern 1}}
  ;            :items {1 [:arrow]}))))
  ;
  ;    (it "wumpus nearby"
  ;      (should=
  ;        {:paths [:north] :hazards-detected [:wumpus]}
  ;        (report "zelda"
  ;          (new-game
  ;            :caverns {1 {:north 2}}
  ;            :players {"zelda" {:cavern 1}}
  ;            :hazards {:wumpus [2]}))))
  ;    )
  )
