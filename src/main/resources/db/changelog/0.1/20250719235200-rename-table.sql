--liquibase formatted sql
--changeset Shulpov Victor:20250719235200-rename-table splitStatements:true

alter table authorities
    rename to authority;
