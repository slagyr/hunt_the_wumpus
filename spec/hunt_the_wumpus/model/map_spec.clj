(ns hunt-the-wumpus.model.map-spec
  (:use
    [speclj.core]
    [hunt-the-wumpus.model.map]))

(describe "Map"

  (it "can detect paths from 1 room map"
    (let [map {1 {}}]
      (should= [] (paths-from map 1))))

  (it "can detect paths from center of cross"
    (let [map {1 {:north 2 :east 3 :south 4 :west 5}}]
      (should= [:north :east :south :west] (paths-from map 1))))

  (it "gets the opposite direction"
    (should= :west (opposite-direction :east))
    (should= :east (opposite-direction :west))
    (should= :north (opposite-direction :south))
    (should= :south (opposite-direction :north)))

  )
