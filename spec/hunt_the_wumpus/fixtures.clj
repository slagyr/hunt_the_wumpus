(ns hunt-the-wumpus.fixtures)

(defrecord GameDriver [])

(defn game-driver []
  (GameDriver.))

(defprotocol AddPath
  (set-start [this start])
  (set-end [this start])
  (set-direction [this start]))

(defrecord MapMaker [paths]
  AddPath
  (set-start [this start])
  (set-end [this end])
  (set-direction [this direction]))

(defn make-map []
  (MapMaker. (atom [])))

(defn clear-map [this]
  )

(defn put-in-cavern [this player location]
  )

(defn enter-command [this command]
  )

(defn cavern-has [this n player]
  )

(defn make-player [this]
  {})

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
