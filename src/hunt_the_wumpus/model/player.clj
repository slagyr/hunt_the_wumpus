(ns hunt-the-wumpus.model.player
  (:use
    [hunt-the-wumpus.model.game :only (caverns players)]))

(defn join-game! [game player-name]
  (dosync
    (let [start-cavern (first (sort (keys (caverns game))))]
      (alter (:players game) assoc player-name {:cavern start-cavern})
      {:ack "You have joined the game"})))

