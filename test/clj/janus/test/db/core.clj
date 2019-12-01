(ns janus.test.db.core
  (:require
   [janus.db.core :refer [*db*] :as db]
   [java-time :as jt]
   [luminus-migrations.core :as migrations]
   [clojure.test :refer :all]
   [clojure.java.jdbc :as jdbc]
   [janus.config :refer [env]]
   [mount.core :as mount]))

(use-fixtures
  :once
  (fn [f]
    (mount/start
     #'janus.config/env
     #'janus.db.core/*db*)
    (migrations/migrate ["migrate"] (select-keys env [:database-url]))
    (f)))

(deftest test-users
  (jdbc/with-db-transaction [t-conn *db*]
    (jdbc/db-set-rollback-only! t-conn)
    (let [now (jt/local-date)
          sample-user {:username "Mitchell"
                       :email "mitchell+001@example.com"
                       :admin true
                       :last_login now
                       :pass "pass2!!/z\\"}
          user-creation-result (db/create-user! t-conn sample-user)
          user-from-db (db/get-user-by-username t-conn
                                                {:username "Mitchell"})
          user-id (:id user-from-db)]

      (testing "Creating new users"
        (is (= 1 user-creation-result))
        (is (= sample-user (dissoc user-from-db :id))) )

      (testing "Retrieving users"
        (is (= (assoc sample-user :id user-id)
               user-from-db
               (db/get-user t-conn {:id user-id}))))

      (testing "Updating users"
        (let [updated-user (assoc user-from-db
                                  :username "Mitchell 2.0"
                                  :admin true
                                  :last_login nil)]
          (is (= 1 (db/update-user! t-conn updated-user)))
          (is (= updated-user
                 (db/get-user t-conn {:id user-id})))))

      (testing "Deleting users"
        (is (= 1 (db/delete-user! t-conn {:id user-id})))
        (is (nil? (db/get-user t-conn {:id user-id})))
        (is (= 0 (db/delete-user! t-conn {:id user-id}))))

      (testing "Malformed user creation fails"
        (is (thrown? Exception
                     (db/create-user! t-conn
                                      {:username nil
                                       :email nil
                                       :admin nil
                                       :last_login nil
                                       :pass nil})))))))

;; Area for REPL interaction
(comment
  (jdbc/with-db-transaction [t-conn *db*]
    (jdbc/db-set-rollback-only! t-conn)
    (let [now (jt/local-date)
          sample-user {:username "itchell"
                       :email "mitchell+001@example.com"
                       :admin true
                       :last_login now
                       :pass "pass2!!/z\\"}]
      (db/delete-user! t-conn {:username "itchell"})
      (db/create-user! t-conn sample-user)
      (Thread/sleep 1000)
      (println (db/get-user t-conn {:username "itchell"})))))
