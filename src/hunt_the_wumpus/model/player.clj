(ns hunt-the-wumpus.model.player
  (:use
    [hunt-the-wumpus.model.map :only (possible-paths)]))

(defn place-player [game player cavern]
  (update-in game [:players player] assoc :cavern cavern))

(defn player-location [game player]
  (:cavern (get (:players game) player)))

(defn join-game [game player-name]
  (let [start-cavern (first (sort (keys (:caverns game))))]
    (place-player game player-name start-cavern)))

(defn move-player-to-location [game player location]
  (update-in game [:players player] assoc :cavern location))

(defn move-player-in-direction [game player direction]
  {:pre [(some #{direction} [:east :west :north :south])]}
  (let [location (player-location game player)]
    (if-let [new-location (-> (possible-paths game location) (get direction))]
      (move-player-to-location game player new-location)
      (throw (Exception. (str "You can't go " (name direction) " from here."))))))

