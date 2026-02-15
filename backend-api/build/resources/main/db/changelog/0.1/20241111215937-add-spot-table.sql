--liquibase formatted sql
--changeset Shulpov Victor:20241111215937-add-spot-table splitStatements:true
CREATE TABLE IF NOT EXISTS spot(
                     id uuid DEFAULT gen_random_uuid() NOT NULL CONSTRAINT spot_pk PRIMARY KEY,
                     "name" varchar(50) NOT NULL,
                     lat DOUBLE PRECISION NOT NULL,
                     lon DOUBLE PRECISION NOT NULL,
                     accepted BOOLEAN NOT NULL DEFAULT FALSE,
                     inserted_at TIMESTAMP NOT NULL DEFAULT current_timestamp,
                     updated_at TIMESTAMP DEFAULT current_timestamp,
                     description varchar(300) NOT NULL
);