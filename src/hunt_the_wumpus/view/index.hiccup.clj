[:div#small-body
 [:h2 "Enter your name"]
 [:form {:method "post" :action "/login"}
  [:input.text {:type "text" :name "player-name"}]
  [:input.submit {:type "submit" :value "Join Game"}]]]