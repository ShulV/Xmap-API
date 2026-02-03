--liquibase formatted sql
--changeset Shulpov Victor:20250718160200-add-user-enabled-default splitStatements:true
alter table "user"
    alter column enabled set default true;

