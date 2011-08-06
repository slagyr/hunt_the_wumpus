(ns hunt-the-wumpus.model.movement)

(defn player-location [game player]
  (:cavern (get @(:players game) player)))

(defn- update-player-location [players player location]
  (update-in players [player] assoc :cavern location))

(defn move-player-to-location! [game player location]
  (dosync
    (alter (:players game) update-player-location player location)))

(defn move-player-in-direction! [game player direction]
  (dosync
    (move-player-to-location! game
                              player
                              (-> @(:caverns game)
                                  (get (player-location game player))
                                  (get direction)))))

