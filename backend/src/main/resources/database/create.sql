CREATE SEQUENCE IF NOT EXISTS user_seq;

CREATE TABLE IF NOT EXISTS users
(
    "id"               	INTEGER 		   PRIMARY KEY NOT NULL,
    "email"             VARCHAR(128)       UNIQUE NOT NULL,
    "password"          VARCHAR(100)       NOT NULL,
    "salt"          	VARCHAR(50)        NOT NULL,
    "first_name"        VARCHAR(128) 	   NOT NULL,
    "refresh_token"     TEXT
);