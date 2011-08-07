[:div#large-body
 [:div#report
  [:p.direction "blah blah"]
  [:p.hazzard "blah blah"]
  [:p.error "blah blah"]]
 [:div#movement
  [:h4 "Move"]
  [:div.north [:a {:href "/command/move/north"} ""]]
  [:div.west [:a {:href "/command/move/west"}]]
  [:div.east [:a {:href "/command/move/north"}]]
  [:div.south [:a {:href "/command/move/south"}]]]
 [:div#shooting
  [:h4 "Shoot"]
  [:div.north [:a {:href "/command/shoot/north"}]]
  [:div.west [:a {:href "/command/shoot/west"}]]
  [:div.east [:a {:href "/command/shoot/north"}]]
  [:div.south [:a {:href "/command/shoot/south"}]]]]