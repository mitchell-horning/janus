(ns janus.env
  (:require
    [selmer.parser :as parser]
    [clojure.tools.logging :as log]
    [janus.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[janus started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[janus has shut down successfully]=-"))
   :middleware wrap-dev})
