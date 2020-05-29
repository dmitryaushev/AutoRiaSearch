CREATE SCHEMA IF NOT EXISTS public;
--CREATING DATABASE
CREATE DATABASE AUTORIA WITH OWNER postgres;
ALTER SCHEMA public OWNER TO postgres;

create table users
(
    id serial,
    first_name varchar(50),
    last_name varchar(50),
    email varchar(50) unique,
    password varchar(100),
    user_role varchar(10),
    user_status varchar(10)
);
alter table users
    owner to postgres;
