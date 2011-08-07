(ns hunt-the-wumpus.model.player
  (:use
    [clojure.set :only (difference)]))

(defn place-player [game player cavern]
  (update-in game [:players player] assoc :cavern cavern))

(defn join-game [game player-name]
  (let [start-cavern (first (sort (keys (:caverns game))))]
    (place-player game player-name start-cavern)))

(defn- new-players [before after]
  (let [players-before (set (keys (:players before)))
        players-after (set (keys (:players after)))]
    (difference players-after players-before)))

(defn- new-player-report [new-player player]
  (if (= new-player player)
    "You have joined the game"
    (format "%s has joined the game" new-player)))

(defn player-report [before after player]
  (let [report []]
    (concat report (map #(new-player-report % player) (new-players before after)))))

