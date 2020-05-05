-- run me first
CREATE DATABASE credIT_db;


-- the rest can be run at once
create table production_company
(
    id serial not null
        constraint production_company_pk
            primary key,
    name varchar(250) not null
);

create table "cast"
(
    id serial not null
        constraint cast_pk
            primary key,
    regdkid varchar(10) not null,
    name varchar(255) not null
);

create table movie
(
    id serial not null
        constraint movie_pk
            primary key,
    name varchar(250) not null,
    release_date date not null
);

create table broadcast
(
    id serial not null
        constraint broadcast_pk
            primary key,
    name varchar(250) not null,
    air_date date not null,
    episode_number integer not null,
    season_number integer not null
);

create table production
(
    id serial not null
        constraint production_pk
            primary key,
    name varchar(255) not null,
    year date not null,
    number_of_seasons integer,
    number_of_episodes integer
);

create table produces
(
    production_company_id integer not null
        constraint produces_production_company_id_fkey
            references production_company,
    production_id integer not null
        constraint produces_production_id_fkey
            references production,
    constraint produces_pkey
        primary key (production_company_id, production_id)
);

create table contains
(
    production_id integer not null
        constraint contains_production_id_fkey
            references production,
    broadcast_id integer not null
        constraint contains_broadcast_id_fkey
            references broadcast,
    constraint contains_pkey
        primary key (production_id, broadcast_id)
);

create table movie_employs
(
    movie_id integer not null
        constraint movie_employs_movie_id_fkey
            references movie,
    cast_id integer not null
        constraint movie_employs_cast_id_fkey
            references "cast",
    role varchar(255) not null,
    constraint movie_employs_pkey
        primary key (movie_id, cast_id)
);

create table broadcast_employs
(
    broadcast_id integer not null
        constraint broadcast_employs_broadcast_id_fkey
            references broadcast,
    cast_id integer not null
        constraint broadcast_employs_cast_id_fkey
            references "cast",
    role varchar(255) not null,
    constraint broadcast_employs_pkey
        primary key (broadcast_id, cast_id)
);