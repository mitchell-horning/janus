-- :name create-user! :! :n
-- :doc creates a new user record
INSERT INTO users
(username, email, admin, last_login, pass)
VALUES (:username, :email, :admin, :last_login, :pass)

-- :name update-user! :! :n
-- :doc updates an existing user record
UPDATE users
SET username = :username, email = :email, admin = :admin, last_login = :last_login, pass = :pass
WHERE id = :id

-- :name get-user-by-username :? :n
-- :doc retrieves a user record given the username
SELECT * FROM users
WHERE username = :username

-- :name get-user :? :n
-- :doc retrieves a user record given the id
SELECT * FROM users
WHERE id = :id

-- :name delete-user! :! :n
-- :doc deletes a user record given the username
DELETE FROM users
WHERE id = :id
