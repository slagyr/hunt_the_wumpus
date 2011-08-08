(ns hunt-the-wumpus.controller.console-controller
  (:use
    [compojure.core]
    [ring.util.response :only (redirect)]
    [joodo.middleware.request :only (*request*)]
    [joodo.views :only (render-template)]))

(defn- show-console []
  (let [report (or (:report (:session *request*)) {})]
    (render-template "/console/show" :report report)))

(defroutes console-controller
  (GET "/console" [] (show-console))
  )



