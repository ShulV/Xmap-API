--liquibase formatted sql
--changeset Shulpov Victor:20250719230200-add-authorities splitStatements:true
CREATE TABLE authorities (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    authority VARCHAR(50) NOT NULL,
    FOREIGN KEY (username) REFERENCES "user"(username)
);