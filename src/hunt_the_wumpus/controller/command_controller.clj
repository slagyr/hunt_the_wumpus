(ns hunt-the-wumpus.controller.command-controller
  (:use
    [compojure.core]
    [ring.util.response :only (redirect)]
    [joodo.middleware.request :only (*request*)]
    [joodo.views :only (render-template)]
    [hunt-the-wumpus.model.game :only (*game* perform-command)]
    [hunt-the-wumpus.model.player :only (go-north go-south go-east go-west)]))

(defn- move-command [direction]
  (cond
    (= "north" direction) go-north
    (= "south" direction) go-south
    (= "east" direction) go-east
    (= "west" direction) go-west))

(defn- move [direction]
  (let [command (move-command direction)
        player (:player-name (:session *request*))
        report (perform-command *game* player command)]
    (-> (redirect "/console")
      (update-in [:session] assoc :report report)
      (update-in [:session] assoc :game @*game*))))

(defn- shoot [direction]
  (-> (redirect "/console")
    (update-in [:session] assoc :report {:error "I don't know how to shoot."})))

(defroutes command-controller
  (GET "/command/move/:direction" {{direction :direction} :params} (move direction))
  (GET "/command/shoot/:direction" {{direction :direction} :params} (shoot direction))
  )