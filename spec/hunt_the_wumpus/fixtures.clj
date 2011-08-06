(ns hunt-the-wumpus.fixtures
  (:use
    [hunt-the-wumpus.model.game :only (create-game caverns players)]
    [hunt-the-wumpus.model.map :only (opposite-direction)]
    [hunt-the-wumpus.model.movement :only (move-player-to-location!
                                           move-player-in-direction!
                                           player-location)]))

(def game (atom (create-game)))

(defprotocol SlimTable
  (execute [this])
  (end-table [this]))

(defprotocol AddPath
  (set-start [this start])
  (set-end [this start])
  (set-direction [this start]))

(defn- translate-direction [command]
  (let [upcase-command (.toUpperCase command)]
    (prn upcase-command)
    ({"W" :west
      "E" :east
      "N" :north
      "S" :south}
     upcase-command)))

(defrecord MapMaker [map start end direction]
  AddPath
  (set-start [this value] (reset! start value))
  (set-end [this value] (reset! end value))
  (set-direction [this value] (reset! direction value))
  SlimTable
  (execute [this]
    (let [dir (translate-direction @direction)]
      (swap! map update-in [@start] assoc dir @end)
      (swap! map update-in [@end] assoc (opposite-direction dir) @start)
           ))
  (end-table [this]
    (dosync
      (ref-set (:caverns @game) @map))))

(defn make-map []
  (MapMaker. (atom {}) (atom nil) (atom nil) (atom nil)))

(defn clear-map [this]
  )

(defn put-in-cavern [this player location]
  (move-player-to-location! @game player location))

(defn enter-command-for [this command player]
  (move-player-in-direction! @game
                             player
                             (translate-direction command)))

(defn cavern-has [this n player]
  (= n
     (player-location @game player)))

(defn message-was-printed [this message]
  )

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
