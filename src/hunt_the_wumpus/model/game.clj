(ns hunt-the-wumpus.model.game
  (:use
    [hunt-the-wumpus.model.report :only (cavern-report hazard-report player-report)]))

(defn new-game [& args]
  (let [options (apply hash-map args)]
    {:caverns (or (:caverns options) {})
     :hazards (or (:hazards options) {})
     :items (or (:items options) {})
     :players (or (:players options) {})}))

(defn- report [before after player]
  {:player-messages (player-report before after player)
   :hazard-messages (hazard-report after player)
   :cavern-messages (cavern-report after player)})

(defn perform-command [game-ref player command-thunk]
  (dosync
    (try
      (let [before @game-ref
            after (alter game-ref command-thunk player)]
        (report before after player))
      (catch Exception e
        (.printStackTrace e)
        {:error (.getMessage e)}))))

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
