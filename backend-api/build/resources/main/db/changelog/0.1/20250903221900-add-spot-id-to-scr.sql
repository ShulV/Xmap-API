--liquibase formatted sql
--changeset Shulpov Victor:20250903221900-add-spot-id-to-scr splitStatements:true
alter table spot_adding_request
    add spot_id uuid
        constraint spot_adding_request_spot_id_fk
            references spot;
