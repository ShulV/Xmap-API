--liquibase formatted sql
--changeset Shulpov Victor:20250903182900-nullable-scr-comment splitStatements:true
alter table spot_adding_request
    alter column comment drop not null;

