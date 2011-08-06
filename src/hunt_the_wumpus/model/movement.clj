(ns hunt-the-wumpus.model.movement)

(defn player-location [game player]
  (:cavern (get @(:players game) player)))

(defn- update-player-location [players player location]
  (update-in players [player] assoc :cavern location))

(defn move-player-to-location! [game player location]
  (dosync
    (alter (:players game) update-player-location player location)))

(defn move-player-in-direction!  [game player direction]
  {:pre [(some #{direction} [:east :west :north :south])]}
  (dosync
    (if-let [new-location (-> @(:caverns game)
                                  (get (player-location game player))
                                  (get direction))]
      (move-player-to-location! game player new-location)
      (alter (:messages game)
             conj
             (str "You can't go " (name direction) " from here.")))))


