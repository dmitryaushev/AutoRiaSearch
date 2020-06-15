CREATE SCHEMA IF NOT EXISTS public;
--CREATING DATABASE
CREATE DATABASE AUTORIA WITH OWNER postgres;
ALTER SCHEMA public OWNER TO postgres;

create table users
(
    id          serial,
    first_name  varchar(50),
    last_name   varchar(50),
    email       varchar(50) unique,
    password    varchar(100),
    user_role   varchar(10),
    user_status varchar(10)
);
alter table users
    owner to postgres;

alter table users
    add primary key (id);

create table search
(
    id         serial,
    price_from varchar(10),
    price_to   varchar(10),
    body_style varchar(10) not null,
    brand      varchar(10) not null,
    model      varchar(10) not null,
    state      varchar(10),
    city       varchar(10),
    type       varchar(10),
    gear_box   varchar(10),
    color      varchar(10),
    top        varchar(10),
    currency   varchar(10),
    user_id    integer,
    foreign key (user_id)
        references users (id)
);

alter table search
    owner to postgres;

alter table search
    add column date timestamp;

alter table search
    add column mailing boolean default false;

create table config
(
    id serial,
    title varchar(25),
    value varchar(25)
);

alter table config
    owner to postgres;