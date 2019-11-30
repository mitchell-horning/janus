CREATE TABLE users
(id VARCHAR(20) PRIMARY KEY,
 username VARCHAR(30),
 email VARCHAR(254),
 admin BOOLEAN,
 last_login TIME,
 pass VARCHAR(300));
