--liquibase formatted sql
--changeset Shulpov Victor:20250727212900-migrate-to-spot-creation-table splitStatements:true
alter table spot drop column accepted;

CREATE TABLE IF NOT EXISTS spot_creation_request(
    id uuid DEFAULT gen_random_uuid() NOT NULL CONSTRAINT spot_creation_request_pk PRIMARY KEY,
    spot_name varchar(50) NOT NULL,
    spot_lat DOUBLE PRECISION NOT NULL,
    spot_lon DOUBLE PRECISION NOT NULL,
    accepted BOOLEAN NOT NULL DEFAULT FALSE,
    inserted_at TIMESTAMP NOT NULL DEFAULT current_timestamp,
    accepted_at TIMESTAMP,
    spot_description varchar(300) NOT NULL,
    comment varchar(300) NOT NULL,
    creator_id uuid NOT NULL
        CONSTRAINT spot_creation_request_creator_id_fk REFERENCES "user",
    acceptor_id uuid
        CONSTRAINT spot_creation_request_acceptor_id_fk REFERENCES "user"
    );
COMMENT ON TABLE spot_creation_request IS 'Заявка на создание спота';

CREATE TABLE IF NOT EXISTS spot_creation_request_s3_file (
    spot_creation_request_id uuid NOT NULL,
    s3_file_id uuid NOT NULL,
    PRIMARY KEY (spot_creation_request_id, s3_file_id),
    FOREIGN KEY (spot_creation_request_id) REFERENCES spot_creation_request(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (s3_file_id) REFERENCES s3_file(id) ON DELETE RESTRICT ON UPDATE CASCADE
);
