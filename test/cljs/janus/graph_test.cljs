(ns janus.graph-test
  (:require [cljs.test :refer-macros [is are deftest testing use-fixtures]]
            [pjstadig.humane-test-output]
            [janus.graph :refer [str->graph]]))

(deftest text->graph-test
  (is (= (str->graph "")
         (str->graph " ")
         (str->graph "\n")
         {:elements []}))

  (is (= (str->graph "a")
         (str->graph " a")
         (str->graph " a ")
         (str->graph " a\n")
         {:elements [{:group "nodes"
                      :data {:id "a"}}]}))

  (let [rem-edge-label (fn [m]
                         (if (= (:group m) "edges")
                           (update m :data dissoc :id)
                           m))
        rem-edge-labels (fn [g]
                          (update g :elements #(map rem-edge-label %)))]
    (is (= (rem-edge-labels (str->graph "a b c"))
           (rem-edge-labels (str->graph "\n a b     c\n"))
           {:elements [{:group "nodes"
                        :data {:id "a"}}
                       {:group "nodes"
                        :data {:id "b"}}
                       {:group "nodes"
                        :data {:id "c"}}
                       {:group "edges"
                        :data {:source "a"
                               :target "b"}}
                       {:group "edges"
                        :data {:source "a"
                               :target "c"}}]}))

    (is (= (rem-edge-labels (str->graph "a b\nb b c\nc a"))
           {:elements [{:group "nodes", :data {:id "a"}}
                       {:group "nodes", :data {:id "b"}}
                       {:group "nodes", :data {:id "c"}}
                       {:group "edges", :data {:source "a" :target "b"}}
                       {:group "edges", :data {:source "b" :target "b"}}
                       {:group "edges", :data {:source "b" :target "c"}}
                       {:group "edges", :data {:source "c" :target "a"}}]}))))
