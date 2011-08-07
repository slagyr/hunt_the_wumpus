(ns hunt-the-wumpus.model.hazard
  (:use
    [hunt-the-wumpus.model.player :only (player-location)]))

(defn place-hazard [game hazard cavern]
  (update-in game [:hazards hazard] conj cavern))



