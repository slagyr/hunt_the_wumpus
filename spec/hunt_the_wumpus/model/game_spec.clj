(ns hunt-the-wumpus.model.game-spec
  (:use
    [speclj.core]
    [hunt-the-wumpus.model.game]
    [hunt-the-wumpus.model.report :only (hazard-report player-report)]))

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
      (should= ["You can go east."] (:cavern-messages report))))

  (it "reports hazards"
    (binding [hazard-report (fn [& args] ["Woohoo!"])]
      (let [report (perform-command (ref (new-game)) "player-1" (fn [game player]))]
        (should= ["Woohoo!"] (:hazard-messages report)))))

  (it "reports player events"
    (binding [player-report (fn [& args] ["Yahoo!"])]
      (let [report (perform-command (ref (new-game)) "player-1" (fn [game player]))]
        (should= ["Yahoo!"] (:player-messages report)))))
    )
