(doctype :html5)
[:html
 [:head
  [:meta {:http-equiv "Content-Type" :content "text/html" :charset "iso-8859-1"}]
  [:title "hunt-the-wumpus"]
  (include-css "/stylesheets/hunt_the_wumpus.css")
  (include-js "/javascript/jquery.min.js" "/javascript/hunt_the_wumpus.js")]
 [:body
  [:div#title
   [:a {:href "/"}
    [:img {:src "/images/title.png"}]]]
  (eval (:template-body joodo.views/*view-context*))
  ]
 ]
