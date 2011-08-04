(ns hunt-the-wumpus.model.map)

(defn paths-from [map room]
  (or
    (keys (get map room))
    []))
