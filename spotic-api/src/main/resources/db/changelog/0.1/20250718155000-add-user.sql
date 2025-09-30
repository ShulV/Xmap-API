--liquibase formatted sql
--changeset Shulpov Victor:20250718155000-add-user splitStatements:true
CREATE TABLE IF NOT EXISTS "user" (
    id uuid DEFAULT gen_random_uuid() NOT NULL
    CONSTRAINT user_pk
    PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(100) NOT NULL,
    enabled BOOLEAN NOT NULL,
    inserted_at TIMESTAMP NOT NULL DEFAULT current_timestamp,
    updated_at TIMESTAMP DEFAULT current_timestamp
);
