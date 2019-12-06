(ns janus.home-page
  (:require
   [reagent.core :as r]
   [re-com.core :refer [box v-box h-box button h-split border
                        title input-textarea modal-panel]]
   [janus.cytoscape-component :refer [cytoscape-graph]]
   [janus.graph :refer [str->graph]]))

(defonce init-graph-str (->> ["entry assembly"
                              "assembly impl1 impl2"
                              "impl1 data1 abstraction1 abstraction2 data2"
                              "impl2 data1 abstraction1 abstraction2 data2"]
                             (interpose "\n")
                             (apply str)))

(defonce graph (r/atom (str->graph init-graph-str)))

(defn title-box []
  [box
   :align :center
   :class "noselect"
   :child [title
           :label "janus"
           :level :level1
           :style {:font-size "32px"}]])

(defn graph-editor [init]
(let [text (r/atom init)]
  [input-textarea
   :height "600px"
   :width "100%"
   :style {:resize "vertical"}
   :change-on-blur? false
   :model text
   :on-change (fn [s]
                (reset! graph (str->graph s))
                (reset! text s))]))

(defn work-space []
  [box
   :size "auto"
   :child [graph-editor init-graph-str]] )

(defn static-info-dialog [label child]
  (let [show? (r/atom false)]
    (fn []
      [v-box
       :children [[button
                   :label label
                   :class "btn btn-info"
                   :on-click #(reset! show? true)]
                  (when @show?
                    [modal-panel
                     :child child
                     :backdrop-on-click #(reset! show? false)])]])))

(defn user-guide-dialog []
  [static-info-dialog
   "User Guide"
   [title
    :label "TODO"
    :level :level2]])

(defn about-dialog []
  [static-info-dialog
   "About"
   [title
    :label "TODO"
    :level :level2]])

(defn info-buttons []
  [h-box
   :align :center
   :justify :start
   :gap "4px"
   :children [[user-guide-dialog]
              [about-dialog]]])

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
