(ns hunt-the-wumpus.model.map)

(def opposite-direction
  {:east :west
   :west :east
   :north :south
   :south :north})

(defn paths-from [map room]
  (or
    (keys (get map room))
    []))
