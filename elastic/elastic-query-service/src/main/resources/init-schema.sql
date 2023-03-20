DROP TABLE IF EXISTS users CASCADE;

CREATE TABLE users
(
    id varchar(36) NOT NULL,
    username varchar(50),
    firstname varchar(50),
    lastname varchar(50),
    CONSTRAINT users_pkey PRIMARY KEY (id)
);

DROP TABLE IF EXISTS documents CASCADE;

CREATE TABLE documents
(
    id varchar(36) NOT NULL,
    document_id varchar(36) NOT NULL,
    CONSTRAINT documents_pkey PRIMARY KEY (id)
);

DROP TABLE IF EXISTS user_permissions CASCADE;

CREATE TABLE user_permissions
(
    user_id varchar(36) NOT NULL,
    document_id VARCHAR(36) NOT NULL,
    user_permission_id VARCHAR(36) NOT NULL,
    permission_type VARCHAR(50)
);

