(ns hunt-the-wumpus.model.report
  (:use
    [clojure.set :only (difference)]
    [hunt-the-wumpus.model.map :only (possible-paths)]
    [hunt-the-wumpus.model.player :only (player-location)]))

(defn cavern-report [game player]
  (map #(format "You can go %s." (name %))
    (keys (possible-paths game (player-location game player)))))

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
