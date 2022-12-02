CREATE SEQUENCE IF NOT EXISTS user_seq;
CREATE SEQUENCE IF NOT EXISTS card_seq;

CREATE TABLE IF NOT EXISTS users
(
    "id"               	INTEGER 		   PRIMARY KEY NOT NULL,
    "email"             VARCHAR(128)       UNIQUE NOT NULL,
    "password"          VARCHAR(100)       NOT NULL,
    "salt"          	VARCHAR(50)        NOT NULL,
    "first_name"        VARCHAR(128) 	   NOT NULL,
    "refresh_token"     TEXT
);

CREATE TABLE IF NOT EXISTS cards
(
    "id"               	INTEGER 		   PRIMARY KEY NOT NULL,
    "owner_id"          INTEGER            NOT NULL,
    "type"              VARCHAR(64)        NOT NULL,
    "number"            VARCHAR(32)        NOT NULL,
    "name"          	VARCHAR(64)        NOT NULL,

    CONSTRAINT fk_card_owner FOREIGN KEY ("owner_id") REFERENCES users (id)
);