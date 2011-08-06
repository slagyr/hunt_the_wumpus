(ns hunt-the-wumpus.model.commands
  (:require [clojure.string :as string]))

(defn translate-direction [command]
  (some
    (fn [[k vs]]
      (if (some #{command} vs)
        k
        nil))
    {:east ["E" "e" "east" "East" "Go East" "go east"]
     :west ["W" "w" "west" "West" "Go West" "go west"]
     :south ["S" "s" "south" "South" "Go South" "go south"]
     :north ["N" "n" "north" "North" "Go North" "go north"]}))


(defn translate-command [command]
  {:command :go
   :direction (translate-direction command)})
