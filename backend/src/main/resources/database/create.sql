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

INSERT INTO users VALUES (1, 'a@bk.ru', '/cGV8UenCa4qCZfH+jbdR64xNyRZw1Fgw7pX+LYcunHSPiThwPH6cpcAVBALyIaviYoISXlo31yo1JT2Bq+htyA==', 'r57lr/kmXr/T/dcK4A6HcYhUwmw2ug5ITcwOq40QixE=', 'Иван', 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiZXhwIjoxNjcwNjAzMDg2fQ.Wo7eb3-aKCJAhfuGMqByfmJwzKZ77NLtF7c6EU6_58k');
INSERT INTO users VALUES (2, 'b@bk.ru', 'AlJZLsPh+HLL0jthndSbeVVUEnuep2AkKD7EsuxOFB/cAS/JTeWz2aMiuubRFvFAVISoe9o78uCV2RId5jrzEw==', 'LwgCi2jdK1Um5+jf5lon1CsfcqslYGEDecs0ZWO7Cvo=', 'Иван', 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiZXhwIjoxNjcwNjAzMDg2fQ.Wo7eb3-aKCJAhfuGMqByfmJwzKZ77NLtF7c6EU6_58k');
