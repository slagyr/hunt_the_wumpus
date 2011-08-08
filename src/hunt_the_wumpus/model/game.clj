(ns hunt-the-wumpus.model.game
  (:use
    [hunt-the-wumpus.model.item :only (items-in add-items unplace-items place-item)]
    [hunt-the-wumpus.model.hazard :only (place-hazard)]
    [hunt-the-wumpus.model.map :only (donut-map)]
    [hunt-the-wumpus.model.player :only (player-location)]
    [hunt-the-wumpus.model.report :only (cavern-report hazard-report player-report item-report)]))

(defn new-game [& args]
  (let [options (apply hash-map args)]
    {:caverns (or (:caverns options) {})
     :hazards (or (:hazards options) {})
     :items (or (:items options) {})
     :players (or (:players options) {})}))

(defn report [before after player]
  {:hazard-messages (hazard-report after player)
   :player-messages (player-report before after player)
   :item-messages (item-report before after player)
   :cavern-messages (cavern-report after player)})

(defn pick-up-items [game player]
  (let [cavern (player-location game player)
        items (items-in game cavern)]
    (-> game
      (add-items player items)
      (unplace-items cavern))))

(defn- update-game [game command-thunk player]
  (-> game
    (command-thunk player)
    (pick-up-items player)))

(defn perform-command [game-ref player command-thunk]
  (dosync
    (try
      (let [before @game-ref
            after (alter game-ref update-game command-thunk player)]
        (report before after player))
      (catch Exception e
        (.printStackTrace e)
        {:error (.getMessage e)}))))

(def *game*
  (ref
    (-> (new-game :caverns (donut-map))
      (place-hazard :wumpus 5)
      (place-item :arrow 3)
      (place-item :arrow 7))))