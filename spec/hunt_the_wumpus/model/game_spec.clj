(ns hunt-the-wumpus.model.game-spec
  (:use
    [speclj.core]
    [hunt-the-wumpus.model.game]))

(describe "Game"

  (it "creating an empty game"
    (let [game (create-game)]
      (should= {} (caverns game))
      (should= {} (hazards game))
      (should= {} (items game))
      (should= {} (players game))
      (should= {} (messages game))))

  (it "creates a populated game"
    (let [game (create-game :caverns {1 {}} :hazards {:pits [1]} :items {:arrows [1]} :players {"joe" {}} :messages {:error "something"})]
      (should= {1 {}} (caverns game))
      (should= {:pits [1]} (hazards game))
      (should= {:arrows [1]} (items game))
      (should= {"joe" {}} (players game))
      (should= {:error "something"} (messages game))))

  (it "performs a command"
    (let [game (atom {:messages (ref {:error "stale"})
                      :caverns (ref {1 {:east 2}})
                      :players (ref {"player-1" {:cavern 1}})})
          result-str (with-out-str
                       (perform-command game
                                        "player-1"
                                        #(print "doing something")))]
      (should= "doing something" result-str)
      (should= {:possible-directions [:east]} @(:messages @game))
             ))


;  (context "reporting"
;
;    (it "status of an empty cavern"
;      (should=
;        {}
;        (report "zelda" (create-game :caverns {1 {}} :players {"zelda" {:cavern 1}}))))
;
;    (it "paths from the current cavern"
;      (should=
;        {:paths [:north, :east, :south, :west]}
;        (report "zelda"
;          (create-game
;            :caverns {1 {:north 2 :east 3 :south 4 :west 5}}
;            :players {"zelda" {:cavern 1}}))))
;
;    (it "arrow found"
;      (should=
;        {:items-found [:arrow]}
;        (report "zelda"
;          (create-game
;            :caverns {1 {}}
;            :players {"zelda" {:cavern 1}}
;            :items {1 [:arrow]}))))
;
;    (it "wumpus nearby"
;      (should=
;        {:paths [:north] :hazards-detected [:wumpus]}
;        (report "zelda"
;          (create-game
;            :caverns {1 {:north 2}}
;            :players {"zelda" {:cavern 1}}
;            :hazards {:wumpus [2]}))))
;    )
  )
