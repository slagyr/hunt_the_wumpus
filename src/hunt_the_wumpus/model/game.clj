(ns hunt-the-wumpus.model.game)

(deftype Game [caverns hazards items players])


(defn create-game [& args]
  (let [options (apply hash-map args)]
    (Game.
      (ref (or (:caverns options) {}))
      (ref (or (:hazards options) {}))
      (ref (or (:items options) {}))
      (ref (or (:players options) {})))))

(defn caverns [game]
  @(.caverns game))

(defn hazards [game]
  @(.hazards game))

(defn items [game]
  @(.items game))

(defn players [game]
  @(.players game))

(defn- report-paths [report game cavern-id]
  (if-let [paths (keys (get (caverns game) cavern-id))]
    (assoc report :paths paths)
    report))

(defn- report-items-found [report game cavern-id]
  (if-let [items-found (get (items game) cavern-id)]
    (assoc report :items-found items-found)
    report))

(defn- report-hazards-detected [report game cavern-id]
  (if-let [adjacent-caverns (vals (get (caverns game) cavern-id))]
    (assoc report :hazards-detected [])
    report))

(defn report [player game]
  (let [cavern-id (:cavern (get (players game) player))]
    (-> {}
      (report-paths game cavern-id)
      (report-items-found game cavern-id))))
