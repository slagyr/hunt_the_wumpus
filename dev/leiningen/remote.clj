(ns leiningen.remote
  (:use
    [gaeshi.cmd :only (java)]
    [leiningen.classpath :only (get-classpath-string)]
    [gaeshi.kuzushi.commands.deploy :only (read-deploy-config)]))

(def app-name "cleancoders")
(def date-format (java.text.SimpleDateFormat. "yyyyMMddHHmmss"))

(defn- usage []
  (println "Remote Leiningen Task Usage:")
  (println "")
  (println "$ lein remote <action> <app/host> [filename]")
  (println "\t actions:  [backup|restore]")
  (println "\t app/host: [host:port|<AppName>] AppName will translate to <AppName>.appspot.com:443"))

(defn- build-host-port [value]
  (let [tokens (.split value ":")]
    (if (= 1 (count tokens))
      [(str value ".appspot.com") 443]
      [(first tokens) (Integer/parseInt (second tokens))])))

(defn remote
  ([project] (usage))
  ([project action] (usage))
  ([project action env & args]
    (let [config (read-deploy-config project)
          classpath (get-classpath-string project)
          classpath (str classpath (System/getProperty "path.separator") (:appengine-sdk-dir config) "/lib/appengine-remote-api.jar")
          jvm-args ["-cp" classpath]
          [host port] (build-host-port env)
          remote-config {:host host :port port :email (:appengine-email config) :password (:appengine-password config)}
          filename (or (first args) (format "backup-%s-%s.dump" host (.format date-format (java.util.Date.))))
          args ["-e" (format "(use 'cleancoders.remote) (%s %s \"%s\")" action remote-config filename)]]
      (java jvm-args "clojure.main" args))))