(ns cleancoders.model.migrations.delete-cart-items-and-possessions
  (:use
    [gaeshi.datastore]
    [cleancoders.remote :only (with-remote-api)]))

;(def source {:host "localhost" :port 8080 :email "admin@localhost.com" :password ""})

;(defn run-migration []
;  (with-remote-api source
;    (println "loading possessions and cart-items")
;    (let [possessions (find-by-kind "possession" :prefetch-size 1000 :chunck-size 1000)
;          _ (println "loaded possessions")
;          cart-items (find-by-kind "cart-item" :prefetch-size 1000 :chunck-size 1000)
;          _ (println "loaded cart-items")]
;      (doseq [group (partition-all 500 possessions)]
;        (println "Deleting" (count group) "possessions")
;        (apply delete group))
;      (doseq [group (partition-all 500 cart-items)]
;        (println "Deleting" (count group) "cart-items")
;        (apply delete group))
;      )))

;(run-migration)
