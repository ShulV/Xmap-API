--liquibase formatted sql
--changeset Shulpov Victor:20250826212900-add-city splitStatements:true

create table if not exists city
(
    id   bigserial not null constraint city_pk primary key,
    name varchar(25) not null
);
comment on table city is 'город';
comment on column city.id is 'идентификатор';
comment on column city.name is 'название города';

alter table city
    add constraint city_name_unique__idx
        unique (name);

comment on constraint city_name_unique__idx on city is 'уникальное название населенного пункта';

insert into city (name) values ('Барнаул') on conflict do nothing;
insert into city (name) values ('Новосибирск') on conflict do nothing;
insert into city (name) values ('Рубцовск') on conflict do nothing;
insert into city (name) values ('Бийск') on conflict do nothing;

alter table if exists spot
    add if not exists city_id bigint default 1 not null
        constraint spot_city_id__fk
            references city;
comment on column spot.city_id is 'идентификатор города';

alter table if exists spot_adding_request
    add if not exists city_id bigint default 1 not null
        constraint spot_adding_request_city_id__fk
            references city;
comment on column spot.city_id is 'идентификатор города';
