(doctype :html5)
[:html
 [:head
  [:meta {:http-equiv "Content-Type" :content "text/html" :charset "iso-8859-1"}]
  [:title "hunt_the_wumpus"]
  (include-css "/stylesheets/hunt_the_wumpus.css")
  (include-js "/javascript/hunt_the_wumpus.js")]
 [:body
  (eval (:template-body joodo.views/*view-context*))
]]