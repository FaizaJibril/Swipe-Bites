BEGIN TRANSACTION;

DROP TABLE IF EXISTS app_users, images cascade;

CREATE TABLE app_users (
   user_id SERIAL,
   username varchar(50) NOT NULL UNIQUE,
   password_hash varchar(200) NOT NULL,
   role varchar(50) NOT NULL,
   CONSTRAINT PK_user PRIMARY KEY (user_id)
);

CREATE TABLE image
(
    image_id SERIAL,
    file_name varchar(200),
    mime_type varchar(100),
    file_size integer,
    file_blob bytea,
    active boolean
) TABLESPACE pg_default;


COMMIT TRANSACTION;
