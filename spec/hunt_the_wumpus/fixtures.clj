(ns hunt-the-wumpus.fixtures
  (:use
    [hunt-the-wumpus.model.game :only (create-game caverns players)]))

(def game (atom (create-game)))

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
  (execute [this] (swap! map update-in [@start] assoc @direction @end))
  (end-table [this]
    (dosync
      (ref-set (.caverns @game) @map))))

(defn make-map []
  (MapMaker. (atom {}) (atom nil) (atom nil) (atom nil)))

(defn clear-map [this]
  )

(defn put-in-cavern [this player location]
  (dosync
    (alter (.players @game) update-in [player] assoc :cavern location)))

(defn enter-command [this command]
  )

(defn cavern-has [this n player]
  )

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
