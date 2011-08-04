(use 'joodo.env)

(def environment {
  :joodo.core.namespace "hunt_the_wumpus.core"
  ; environment settings go here
  })

(swap! *env* merge environment)