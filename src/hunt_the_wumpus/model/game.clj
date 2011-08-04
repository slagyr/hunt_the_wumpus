(ns hunt-the-wumpus.model.game)

(deftype Game [caverns hazzards items players])

(defn create-game [& args]
  (let [options (apply hash-map args)]
    (Game.
      (ref (or (:caverns options) {}))
      (ref (or (:hazzards options) {}))
      (ref (or (:items options) {}))
      (ref (or (:players options) {})))))

(defn caverns [game]
  @(.caverns game))

(defn hazzards [game]
  @(.hazzards game))

(defn items [game]
  @(.items game))

(defn players [game]
  @(.players game))

(defn report [game player]
  [])