;(ns cleancoders.model.migrations.possessions-and-cart-items-to-licenses
;  (:use
;    [gaeshi.datastore]
;    [cleancoders.model.license]
;    [cleancoders.remote :only (with-remote-api)])
;  (require
;    [cleancoders.model.cart]))
;
;
;(defn item->license [item cart]
;  (let [state (if (= "closed" (:state cart)) "active" "pending")]
;    (license :item-key (:item-key item) :viewer-key (:viewer-key cart) :level "personal" :quantity 1 :unit-price (:personal-price item) :state state :created-at (:created-at item))))
;
;(defn- create-licenses [carts]
;  (reduce
;    (fn [licenses cart]
;      (concat licenses (map #(item->license % cart) (:items cart))))
;    []
;    carts))
;
;(defn- pseudo-key [v-source i-source]
;  (str
;    (.getId (:viewer-key v-source))
;    ":"
;    (.getId (:item-key i-source))))
;
;(defn- map-licenses [licenses m]
;  (reduce #(assoc %1 (pseudo-key %2 %2) %2) m licenses))
;
;(defn- add-contents-to-cart [cart license-map]
;  (assoc cart :content-keys
;    (vec
;      (map :key
;        (map
;          (fn [item]
;            (let [key (pseudo-key cart item)]
;              (get license-map key)))
;          (:items cart))))))
;
;(defn- save-licenses [license-map]
;  (reduce
;    (fn [saved-map group]
;      (println "Saving group of" (count group) "licenses")
;      (map-licenses
;        (save-many group)
;        saved-map))
;    {}
;    (partition-all 500 (vals license-map))))
;
;(defn possession->license [possession]
;  (license :item-key (:item-key possession) :viewer-key (:viewer-key possession) :level "personal" :quantity 1 :unit-price 0 :state "active" :created-at (:created-at possession)))
;
;(defn- licenses-from-possessions [possessions license-map]
;  (reduce
;    (fn [l-map possession]
;      (let [key (pseudo-key possession possession)]
;        (if (get l-map key)
;          l-map
;          (assoc l-map key (possession->license possession)))))
;    license-map
;    possessions))
;
;
;;(def source {:host "localhost" :port 8080 :email "admin@localhost.com" :password ""})
;
;(defn run-migration []
;  (with-remote-api source
;    (let [possessions (find-by-kind "possession" :prefetch-size 1000 :chunck-size 1000)
;          _ (println "loaded possessions")
;          cart-items (find-by-kind "cart-item" :prefetch-size 1000 :chunck-size 1000)
;          _ (println "loaded cart-items")
;          item-map (reduce #(assoc %1 (:cart-key %2) (conj (or (get %1 (:cart-key %2)) []) %2)) {} cart-items)
;          _ (println "mapped items")
;          carts (find-by-kind "cart" :prefetch-size 1000 :chunck-size 1000)
;          _ (println "loaded carts")
;          carts (map #(assoc % :items (get item-map (:key %))) carts)
;          _ (println "creating licenses from carts")
;          licenses (create-licenses carts)
;          _ (println "licenses created")
;          license-map (map-licenses licenses {})
;          _ (println "licenses mapped")
;          license-map (licenses-from-possessions possessions license-map)
;          _ (println "licenses created from possessions")
;          saved-license-map (save-licenses license-map)
;          carts (map #(add-contents-to-cart % saved-license-map) carts)]
;      (doseq [group (partition-all 500 carts)]
;        (println "Saving group of" (count group) "carts")
;        (save-many group))
;      )))
;
;;(run-migration)