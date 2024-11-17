--liquibase formatted sql
--changeset Shulpov Victor:20241116121137 splitStatements:true
CREATE TABLE s3_file
(
    id uuid DEFAULT gen_random_uuid() NOT NULL
            CONSTRAINT s3_file_pk
            PRIMARY KEY,
    original_file_name VARCHAR(255)                        NOT NULL,
    file_size          BIGINT                              NOT NULL,
    file_content       bytea,
    file_extension     VARCHAR(10)                         NOT NULL,
    file_type          VARCHAR(15)                         NOT NULL,
    uploaded_at        TIMESTAMP DEFAULT current_timestamp NOT NULL,
    s3_object_key      uuid,
    uploaded_in_s3     BOOLEAN DEFAULT FALSE               NOT NULL,
    uploaded_in_s3_at  TIMESTAMP
);

COMMENT ON TABLE s3_file IS 'Временная таблица для файлов для последующей отправки в S3 воркером';

COMMENT ON COLUMN s3_file.id IS 'Уникальный идентификатор файла в xmap';

COMMENT ON COLUMN s3_file.original_file_name IS 'Имя файла при загрузке';

COMMENT ON COLUMN s3_file.file_size IS 'Размер файла в байтах';

COMMENT ON COLUMN s3_file.file_content IS 'Содержимое файла (байты)';

COMMENT ON COLUMN s3_file.file_extension IS 'Расширение файла';

COMMENT ON COLUMN s3_file.file_type IS 'Тип файла в xmap';

COMMENT ON COLUMN s3_file.uploaded_at IS 'Дата выгрузки в xmap';

COMMENT ON COLUMN s3_file.s3_object_key IS 'Уникальный идентификатор в s3';

COMMENT ON COLUMN s3_file.uploaded_in_s3 IS 'Выгружено ли в s3?';

COMMENT ON COLUMN s3_file.uploaded_in_s3_at IS 'Дата выгрузки в xmap';