create table credit_schema.production_company
(
    id   serial       not null
        constraint production_company_pk
            primary key,
    name varchar(250) not null
);

alter table credit_schema.production_company
    owner to postgres;

create table credit_schema."cast"
(
    id      serial       not null
        constraint cast_pk
            primary key,
    regdkid varchar(10)  not null,
    name    varchar(255) not null
);

alter table credit_schema."cast"
    owner to postgres;

create table credit_schema.movie
(
    id           serial       not null
        constraint movie_pk
            primary key,
    name         varchar(250) not null,
    release_date date         not null
);

alter table credit_schema.movie
    owner to postgres;

create table credit_schema.broadcast
(
    id             serial       not null
        constraint broadcast_pk
            primary key,
    name           varchar(250) not null,
    air_date       date         not null,
    episode_number integer      not null,
    season_number  integer      not null
);

alter table credit_schema.broadcast
    owner to postgres;

create table credit_schema.production
(
    id                 serial       not null
        constraint production_pk
            primary key,
    name               varchar(255) not null,
    year               date         not null,
    number_of_seasons  integer,
    number_of_episodes integer
);

alter table credit_schema.production
    owner to postgres;

create table credit_schema.produces
(
    production_company_id integer not null
        constraint produces_production_company_id_fkey
            references credit_schema.production_company,
    production_id         integer not null
        constraint produces_production_id_fkey
            references credit_schema.production,
    constraint produces_pkey
        primary key (production_company_id, production_id)
);

alter table credit_schema.produces
    owner to postgres;

create table credit_schema.contains
(
    production_id integer not null
        constraint contains_production_id_fkey
            references credit_schema.production,
    broadcast_id  integer not null
        constraint contains_broadcast_id_fkey
            references credit_schema.broadcast,
    constraint contains_pkey
        primary key (production_id, broadcast_id)
);

alter table credit_schema.contains
    owner to postgres;

create table credit_schema.movie_employs
(
    movie_id integer      not null
        constraint movie_employs_movie_id_fkey
            references credit_schema.movie,
    cast_id  integer      not null
        constraint movie_employs_cast_id_fkey
            references credit_schema."cast",
    role     varchar(255) not null,
    constraint movie_employs_pkey
        primary key (movie_id, cast_id)
);

alter table credit_schema.movie_employs
    owner to postgres;

create table credit_schema.broadcast_employs
(
    broadcast_id integer      not null
        constraint broadcast_employs_broadcast_id_fkey
            references credit_schema.broadcast,
    cast_id      integer      not null
        constraint broadcast_employs_cast_id_fkey
            references credit_schema."cast",
    role         varchar(255) not null,
    constraint broadcast_employs_pkey
        primary key (broadcast_id, cast_id)
);

alter table credit_schema.broadcast_employs
    owner to postgres;

