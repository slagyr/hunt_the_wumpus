(let [report (:report *view-context*)]
  (list
    [:div#large-body
     [:div#report
      (when (:error report)
        [:p.error (:error report)])
      (for [message (:game-over-messages report)]
        [:p.game-over message])
      (for [message (:hazard-messages report)]
        [:p.hazard message])
      (for [message (:player-messages report)]
        [:p.player message])
      (for [message (:item-messages report)]
        [:p.item message])
      (for [message (:cavern-messages report)]
        [:p.cavern message])]
     [:div#movement
      [:h4 "Move"]
      [:div.north [:a {:href "/command/move/north"} ""]]
      [:div.west [:a {:href "/command/move/west"}]]
      [:div.east [:a {:href "/command/move/east"}]]
      [:div.south [:a {:href "/command/move/south"}]]]
     [:div#decor
      [:pre "
     .           .
     |           |
     ||         ||
     ||         ||
    /~~\\       /~~\\
   |    \\_____/    |
   |  II        II  |
   |  II        II  |
   |  II        II  |
   |               /
   |        ~~-~~|~
   |       {     !
   |       {  !
   |        ~~!~
   |             \\
    \\            /
      \\        /
        =====
       (     )
    ---{     }---
        +++++
         | |
         | |

      "]]
     [:div#shooting
      [:h4 "Shoot"]
      [:div.north [:a {:href "/command/shoot/north"}]]
      [:div.west [:a {:href "/command/shoot/west"}]]
      [:div.east [:a {:href "/command/shoot/east"}]]
      [:div.south [:a {:href "/command/shoot/south"}]]]]
    )
  )
