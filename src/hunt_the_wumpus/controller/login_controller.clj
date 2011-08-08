(ns hunt-the-wumpus.controller.login-controller
  (:use
    [compojure.core]
    [ring.util.response :only (redirect)]
    [joodo.middleware.request :only (*request*)]
    [joodo.views :only (render-template)]
    [hunt-the-wumpus.model.game :only (*game* perform-command)]
    [hunt-the-wumpus.model.player :only (join-game)]))

(defn- successful-login [name]
  (let [report (perform-command *game* name join-game)]
    (-> (redirect "/console")
      (update-in [:session] assoc :player-name name :report report))))

(defn- process-login []
  (let [params (:params *request*)
        name (:player-name params)]
    (if (not (empty? (str name)))
      (successful-login name)
      (redirect "/"))))

(defroutes login-controller
  (POST "/login" [] (process-login))
  )


