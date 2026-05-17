--liquibase formatted sql
--changeset Shulpov Victor:20260517140800-delete-spot-desc-required splitStatements:true
alter table spot_adding_request
    alter column spot_description drop not null;

alter table spot
    alter column description drop not null;

