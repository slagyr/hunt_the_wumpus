(ns hunt_the_wumpus.core-spec
  (:use
    [speclj.core]
    [joodo.spec-helpers.controller]
    [hunt_the_wumpus.core]))

(describe "hunt_the_wumpus"

  (with-mock-rendering)
  (with-routes app-handler)

  (it "handles home page"
    (let [result (do-get "/")]
      (should= 200 (:status result))
      (should= "index" @rendered-template)))
  )

(run-specs)
