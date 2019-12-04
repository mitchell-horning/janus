(ns janus.home-page
  (:require
   [reagent.core :as r]
   [re-com.core :refer [box v-box h-box button h-split border
                        title input-textarea]]
   [janus.cytoscape-graph :refer [cytoscape-graph]]))

(defonce graph (r/atom {:elements [{:group "nodes" :data {:id "a"}}
                                   {:group "nodes" :data {:id "b"}}
                                   {:group "nodes" :data {:id "c"}}
                                   {:group "edges" :data {:id "ab"
                                                          :source "a"
                                                          :target "b"}}]}))

(defn title-box []
  [box
   :align :center
   :class "noselect"
   :child [title
           :label "janus"
           :level :level1
           :style {:font-size "32px"}]])

(defn work-space []
  [box
   :size "auto"
   :child [input-textarea
           :height "600px"
           :width "100%"
           :style {:resize "vertical"}
           :model (pr-str @graph)
           :on-change #()]] )

(defn info-buttons []
  [h-box
   :align :center
   :justify :start
   :gap "4px"
   :children [[button :class "btn btn-info" :label "User Guide"]
              [button :class "btn btn-info" :label "About"]]])

(defn top-bar []
  [h-box
   :height "40px"
   :class "bg-info"
   :align :center
   :justify :between
   :children [[info-buttons]
              [button :class "btn btn-default" :label "Log In"]]])

(defn home-page []
  [h-split
   :height "100%"
   :splitter-size "2px"
   :initial-split 20
   :margin "0px"
   :panel-1 [v-box
             :size "auto"
             :class "bg-secondary"
             :children [[title-box]
                        [work-space]]]
   :panel-2 [v-box
             :size "auto"
             :class "bg-primary"
             :children [[top-bar]
                        [cytoscape-graph @graph]]]])
