--liquibase formatted sql
--changeset Shulpov Victor:20241117145637 splitStatements:true
CREATE TABLE spot_s3_file (
      spot_id uuid NOT NULL,
      s3_file_id uuid NOT NULL,
      PRIMARY KEY (spot_id, s3_file_id),
      FOREIGN KEY (spot_id) REFERENCES spot(id) ON DELETE RESTRICT ON UPDATE CASCADE,
      FOREIGN KEY (s3_file_id) REFERENCES s3_file(id) ON DELETE RESTRICT ON UPDATE CASCADE
);