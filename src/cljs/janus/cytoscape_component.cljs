(ns janus.cytoscape-component
  (:require [reagent.core :as r]
            ["cytoscape" :as cytoscape]
            ["cytoscape-cise" :as cise]))

(defn- cytoscape-graph-inner []
  (let [cy (atom nil)
        defaults {:style
                  [{:selector "node"
                    :style {"background-color" "black"
                            "color" "white"
                            "label" "data(id)"
                            "text-halign" "center"
                            "text-valign" "center"}}
                   {:selector "edge"
                    :style {"line-color" "black"
                            "curve-style" "bezier"
                            "target-arrow-shape" "triangle"
                            "target-arrow-color" "black"}}]}
        run-layout (fn [cytoscape-atom]
                     (-> @cy
                         (.elements "node")
                         (.layout (clj->js {:name "cise"}))
                         (.run)))]
    (r/create-class
     {:display-name "cytoscape-inner"

      :reagent-render
      (fn [] [:div {:style {:height "100%"}}])

      :component-did-mount
      (fn [this]
        (let [config (merge defaults
                            {:container (r/dom-node this)}
                            (r/props this))]
          (reset! cy (cytoscape (clj->js config)))
          (.use cytoscape cise)
          (run-layout cy)))

      :component-did-update
      (fn [this]
        (-> @cy (.elements "node") .remove)
        (run! #(.add @cy (clj->js %))
              (:elements (r/props this) ))
        (if-not (empty? (:elements (r/props this)))
          (run-layout cy)))})))

(defn cytoscape-graph []
  (fn [g] [cytoscape-graph-inner g]))
