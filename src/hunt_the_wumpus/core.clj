(ns hunt-the-wumpus.core
  (:use
    [compojure.core :only (defroutes GET)]
    [compojure.route :only (not-found)]
    [joodo.middleware.view-context :only (wrap-view-context)]
    [joodo.views :only (render-template render-html)]
    [joodo.controllers :only (controller-router)]))

(defroutes hunt-the-wumpus-routes
  (GET "/" [] (render-template "index"))
  (controller-router 'hunt-the-wumpus.controller)
  (not-found (render-template "not_found" :template-root "hunt_the_wumpus/view" :ns `hunt-the-wumpus.view.view-helpers)))

(def app-handler
  (->
    hunt-the-wumpus-routes
    (wrap-view-context :template-root "hunt_the_wumpus/view" :ns `hunt-the-wumpus.view.view-helpers)))



