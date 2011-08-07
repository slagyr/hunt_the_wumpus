(ns hunt-the-wumpus.model.game
  (:use [hunt-the-wumpus.model.movement :only (possible-directions)]))

(defrecord Game [caverns hazards items players messages])

(defn create-game [& args]
  (let [options (apply hash-map args)]
    (Game.
      (ref (or (:caverns options) {}))
      (ref (or (:hazards options) {}))
      (ref (or (:items options) {}))
      (ref (or (:players options) {}))
      (ref (or (:messages options) {})))))

(defn caverns [game]
  @(:caverns game))

(defn hazards [game]
  @(:hazards game))

(defn items [game]
  @(:items game))

(defn players [game]
  @(:players game))

(defn messages [game]
  @(:messages game))

(defn perform-command [game player command-thunk]
  (dosync
    (ref-set (:messages @game) {})

    (let [result (command-thunk)]

      ; TODO: add game-over messages instead if that's the outcome

      (alter (:messages @game)
             assoc
             :possible-directions
             (possible-directions @game player))

      result)))

;(defn- report-paths [report game cavern-id]
;  (if-let [paths (keys (get (caverns game) cavern-id))]
;    (assoc report :paths paths)
;    report))
;
;(defn- report-items-found [report game cavern-id]
;  (if-let [items-found (get (items game) cavern-id)]
;    (assoc report :items-found items-found)
;    report))
;
;(defn- report-hazards-detected [report game cavern-id]
;  (let [adjacent-caverns (vals (get (caverns game) cavern-id))
;        hazards (hazards game)]
;    (if-let [hazards-detected (keys
;                                (filter (fn [[hazard-name locations]]
;                                          (some (set locations)
;                                                adjacent-caverns))
;                                        hazards))]
;      (assoc report :hazards-detected hazards-detected)
;      report)))
;
;(defn report [player game]
;  (let [cavern-id (:cavern (get (players game) player))]
;    (-> {}
;      (report-paths game cavern-id)
;      (report-items-found game cavern-id)
;      (report-hazards-detected game cavern-id))))
;
