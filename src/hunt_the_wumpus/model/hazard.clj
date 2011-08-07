(ns hunt-the-wumpus.model.hazard
  (:use
    [hunt-the-wumpus.model.movement :only (player-location)]))

(defn place-hazard [game hazard cavern]
  (update-in game [:hazards hazard] conj cavern))

(defmulti report-hazard identity)
(defmethod report-hazard :wumpus [hazard]
  "You smell the Wumpus.")

(defn- hazards-adjacent-to [game origin]
  (let [adjacent-caverns (set (vals (get (:caverns game) origin)))
        adjacent? (fn [[hazard caverns]] (some adjacent-caverns caverns))
        adjacent-hazard-pairs (filter adjacent? (:hazards game))]
    (map first adjacent-hazard-pairs)))

(defn hazard-report [game player]
  (let [origin (player-location game player)
        nearby-hazards (hazards-adjacent-to game origin)]
    (map report-hazard nearby-hazards)))

