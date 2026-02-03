--liquibase formatted sql
--changeset Shulpov Victor:xmap-24 splitStatements:true
CREATE TABLE IF NOT EXISTS custom_locale (
        locale_code VARCHAR(10) PRIMARY KEY,
        description VARCHAR(50) NOT NULL,

        is_default BOOLEAN NOT NULL DEFAULT FALSE
);

INSERT INTO custom_locale (locale_code, description, is_default) VALUES
    ('en', 'English', FALSE),
    ('ru', 'Russian', TRUE)
ON CONFLICT DO NOTHING;
