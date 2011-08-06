(ns hunt-the-wumpus.model.map)

(def opposite-direction
  {:east :west
   :west :east
   :north :south
   :south :north})

(defn add-paths [caverns & {:keys [start end direction]}]
  (-> caverns
      (update-in [start] assoc direction end)
      (update-in [end] assoc (opposite-direction direction) start)))

(defn paths-from [map room]
  (or
    (keys (get map room))
    []))
