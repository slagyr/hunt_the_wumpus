(ns hunt-the-wumpus.controller.console-controller
  (:use
    [compojure.core]
    [ring.util.response :only (redirect)]
    [joodo.middleware.request :only (*request*)]
    [joodo.views :only (render-template)]))

(defroutes console-controller
  (GET "/console" [] (render-template "/console/show"))
  )



