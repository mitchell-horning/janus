(ns janus.graph
  (:require
   [clojure.test.check :as tc]
   [clojure.spec.alpha :as s]
   [clojure.spec.gen.alpha :as gen]
   [clojure.string :refer [split split-lines trim]]))

(s/def ::group #{"edges" "nodes"})

(s/def ::id string?)
(s/def ::source string?)
(s/def ::target string?)

(s/def ::data (s/keys :req-un [::id]
                      :opt-un [::source ::target]))

(s/def ::element (s/keys :req-un [::group ::data]))

(s/def ::graph (s/coll-of ::element :kind vector?))

(s/fdef str->graph
  :args string?
  :ret ::graph)

(defn split-whitespace [s]
  (split s #"\s+"))

(defn tokenize [s]
  (->> s split-lines (map trim) (map split-whitespace)))

(defn edgecate [[f & r]]
  (map #(vector f %) r))

(defn vec-to-edge [token]
  (let [[source target] token]
    {:group "edges"
     :data {:id (gensym)
            :source source
            :target target}}))

(defn to-element [token]
  (cond
    (= "" token) nil
    (vector? token) (vec-to-edge token)
    :else {:group "nodes"
           :data {:id (str token)}}))

(defn str->graph [s]
  (let [tokens (tokenize s)
        nodes (into #{} cat tokens)
        edges (mapcat edgecate tokens)]
    {:elements (into [] (comp (map to-element) (remove nil?)) (concat nodes edges))}))
