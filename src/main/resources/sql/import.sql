CREATE TABLE ACCOUNT (account_id BIGINT NOT NULL, first_name VARCHAR(16) NOT NULL, last_name VARCHAR(16) NOT NULL, login VARCHAR(64) NOT NULL, password VARCHAR(128) NOT NULL, role BIGINT NOT NULL, PRIMARY KEY (account_id), UNIQUE (login));
CREATE TABLE ACCOUNT_TYPE (type_id BIGINT NOT NULL, name VARCHAR(16) NOT NULL, account_id BIGINT NOT NULL, PRIMARY KEY (type_id), UNIQUE (name, account_id));
CREATE TABLE ACCOUNT_LOGIN (id BIGINT NOT NULL, account_id BIGINT NOT NULL, account_name VARCHAR(64) NOT NULL, type_id BIGINT, login VARCHAR(64) NOT NULL, password VARCHAR(128) NOT NULL, url VARCHAR(512), modification_date DATE NOT NULL, PRIMARY KEY (id), UNIQUE (account_id, account_name));

ALTER TABLE ACCOUNT_LOGIN ADD CONSTRAINT FK_ACCOUNT_LOGIN_TO_ACCOUNT FOREIGN KEY (account_id) REFERENCES ACCOUNT (account_id) ON DELETE CASCADE;
ALTER TABLE ACCOUNT_TYPE ADD CONSTRAINT FK_ACCOUNT_TYPE_TO_ACCOUNT FOREIGN KEY (account_id) REFERENCES ACCOUNT (account_id) ON DELETE CASCADE;
ALTER TABLE ACCOUNT_LOGIN ADD CONSTRAINT FK_ACCOUNT_LOGIN_TO_ACCOUNT_TYPE FOREIGN KEY (TYPE_ID) REFERENCES ACCOUNT_TYPE(TYPE_ID) ON DELETE CASCADE;
INSERT INTO ACCOUNT VALUES (1, 'Admin', 'KeyManager', 'admin', 'lLdleJQU6p8Ju4VKQ2aGTQ==', 1);