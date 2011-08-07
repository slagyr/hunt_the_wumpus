(ns hunt-the-wumpus.fixtures
  (:require [clojure.string :as string])
  (:use
    [hunt-the-wumpus.model.commands :only (translate-direction
      translate-command)]
    [hunt-the-wumpus.model.game :as game :only (perform-command)]
    [hunt-the-wumpus.model.map :only (add-paths opposite-direction)]
    [hunt-the-wumpus.model.movement :only (move-player-to-location
      move-player-in-direction
      player-location)]))

(def game-ref (ref (game/new-game)))
(def last-report (atom nil))

(defprotocol SlimTable
  (execute [this])
  (end-table [this]))

(defprotocol AddPath
  (set-start [this start])
  (set-end [this start])
  (set-direction [this start]))

(defrecord MapMaker [map start end direction]
  AddPath
  (set-start [this value] (reset! start value))
  (set-end [this value] (reset! end value))
  (set-direction [this value] (reset! direction value))
  SlimTable
  (execute [this]
    (swap! map add-paths
      :start @start
      :end @end
      :direction (translate-direction @direction)))
  (end-table [this]
    (dosync
      (alter game-ref assoc :caverns @map))))

(defn make-map []
  (MapMaker. (atom {}) (atom nil) (atom nil) (atom nil)))

(defn clear-map [this]
  )

(defn put-in-cavern [this player location]
  (dosync
    (alter game-ref move-player-to-location player location)))

(defn- command-spec->thunk [command-spec player]
  (cond
    (= :go (:command command-spec))
    (fn [game player] (move-player-in-direction game player (:direction command-spec)))
    (= :rest (:command command-spec))
    (fn [game player] game)
    :else
    (fn [game player] (throw Exception command-spec))))

(defn enter-command-for [this raw-command player]
  (let [command-spec (translate-command raw-command)
        thunk (command-spec->thunk command-spec player)
        report (perform-command game-ref player thunk)]
    (reset! last-report report)))

(defn error-message [this]
  (:error @last-report))

(defn possible-directions-message [this]
  (string/join ","
    (sort
      (map name
        (:possible-directions @last-report)))))

(defn cavern-has [this n player]
  (println "n: " n)
  (println "player: " player)
  (println "@game-ref: " @game-ref)
  (= n (player-location @game-ref player)))

(defn message-was-printed [this message]
  (boolean
    (some #{message}
      @last-report)))

(defn freeze-wumpus [this v]
  )

(defn set-quiver-to [this quiver]
  )

(defn arrows-in-cavern [this cavern]
  )

(defn arrows-in-quiver [this]
  )

(defn game-terminated [this]
  )

(defn new-game [this]
  )

(defn check-random-wumpus-movement []
  )

(defn set-cavern [this cavern]
  )

(defn check-random-bat-transport []
  )
