(ns janus.cytoscape-graph
  (:require [reagent.core :as r]
            ["cytoscape" :as cytoscape]))

(defn- cytoscape-graph-inner []
  (let [cy (atom nil)
        defaults {:style
                  [{:selector "node"
                    :style {"background-color" "black"
                            "label" "data(id)"}}
                   {:selector "edge"
                    :style {"line-color" "black"
                            "curve-style" "bezier"
                            "target-arrow-shape" "triangle"
                            "target-arrow-color" "black"}}]
                  :layout
                  {:name "circle" :rows 1}}]
    (r/create-class
     {:display-name "cytoscape-inner"

      :reagent-render
      (fn [] [:div {:style {:height "100%"}}])

      :component-did-mount
      (fn [this]
        (let [config (merge defaults
                            {:container (r/dom-node this)}
                            (r/props this))]
          (reset! cy (cytoscape (clj->js config)))))

      :component-did-update
      (fn [this]
        (-> @cy (.elements "node") .remove)
        (run! #(.add @cy (clj->js %))
              (:elements (r/props this) ))
        (-> @cy
            (.elements "node")
            (.layout (clj->js {:name "circle"}))
            (.run)))})))

(defn cytoscape-graph []
  (fn [g] [cytoscape-graph-inner g]))
