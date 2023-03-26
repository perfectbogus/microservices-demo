DROP TABLE IF EXISTS user_permissions CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS documents CASCADE;

CREATE TABLE users
(
    id varchar(36) NOT NULL,
    username varchar(50),
    firstname varchar(50),
    lastname varchar(50),
    CONSTRAINT users_pkey PRIMARY KEY (id)
);

CREATE TABLE documents
(
    id varchar(36) NOT NULL,
    document_id varchar(36) NOT NULL,
    CONSTRAINT documents_pkey PRIMARY KEY (id)
);

CREATE TABLE user_permissions
(
    user_id varchar(36) NOT NULL,
    document_id VARCHAR(36) NOT NULL,
    user_permission_id VARCHAR(36) NOT NULL,
    permission_type VARCHAR(50),
    CONSTRAINT document_fk FOREIGN KEY (document_id)
        REFERENCES documents (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT user_fk FOREIGN KEY (user_id)
        REFERENCES users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);