(ns hunt-the-wumpus.controller.login-controller
  (:use
    [compojure.core]
    [ring.util.response :only (redirect)]
    [joodo.middleware.request :only (*request*)]
    [joodo.views :only (render-template)]))

(defn- successful-login [name]
  (assoc
    (redirect "/console")
    :session {:player-name name}))

(defn- process-login []
  (let [params (:params *request*)
        name (:player-name params)]
    (if (not (empty? (str name)))
      (successful-login name)
      (redirect "/"))))

(defroutes login-controller
  (POST "/login" [] (process-login))
  )


