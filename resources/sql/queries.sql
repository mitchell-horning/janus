-- :name create-user! :! :n
-- :doc creates a new user record
INSERT INTO users
(id, username, email, admin, last_login, pass)
VALUES (:id, :username, :email, :admin, :last_login, :pass)

-- :name update-user! :! :n
-- :doc updates an existing user record
UPDATE users
SET username = :username, email = :email, admin = :admin, last_login = :last_login, pass = :pass
WHERE id = :id

-- :name get-user :? :1
-- :doc retrieves a user record given the id
SELECT * FROM users
WHERE id = :id

-- :name delete-user! :! :n
-- :doc deletes a user record given the id
DELETE FROM users
WHERE id = :id
