(use 'joodo.env)

(def environment {
  :joodo.core.namespace "hunt-the-wumpus.core"
  ; environment settings go here
  })

(swap! *env* merge environment)