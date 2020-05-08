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

create table cast_members
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
            references cast_members,
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
            references cast_members,
    role varchar(255) not null,
    constraint broadcast_employs_pkey
        primary key (broadcast_id, cast_id)
);

-- update single broadcast season number
CREATE OR REPLACE PROCEDURE update_season_number(broadcast_name varchar)
AS $$
DECLARE
    number_of_seasons_temp integer := 1;
BEGIN
    SELECT max(season_number) INTO number_of_seasons_temp FROM broadcast WHERE broadcast.name = broadcast_name;
    UPDATE production SET number_of_seasons = number_of_seasons_temp WHERE name = broadcast_name;
END; $$
    LANGUAGE plpgsql;

-- update single broadcast episode number
CREATE OR REPLACE PROCEDURE update_episode_number(broadcast_name varchar)
AS $$
DECLARE
    number_of_episodes_temp integer := 1;
BEGIN
    SELECT count(episode_number) INTO number_of_episodes_temp FROM broadcast WHERE broadcast.name = broadcast_name;
    UPDATE production SET number_of_episodes = number_of_episodes_temp WHERE name = broadcast_name;
END; $$
    LANGUAGE plpgsql;

-- update all broadcasts procedure
CREATE OR REPLACE PROCEDURE update_all_broadcast_sizes()
AS $$
DECLARE
    broadcasts CURSOR FOR SELECT DISTINCT(name) AS name FROM broadcast;
BEGIN
    FOR broadcast in broadcasts LOOP
            CALL update_season_number(broadcast.name);
            CALL update_episode_number(broadcast.name);
        END LOOP;
END; $$
    LANGUAGE plpgsql;

-- function wrapper to enable running procedure as trigger
CREATE OR REPLACE FUNCTION update_all_broadcast_sizes_trigger()
    RETURNS TRIGGER
AS $$
BEGIN
    CALL update_all_broadcast_sizes();
    RETURN NULL;
END; $$
    LANGUAGE plpgsql;

-- Define trigger to update broadcast(number_of_seasons and number_of_episodes)
CREATE TRIGGER update_size_of_broadcast_trigger
    AFTER INSERT OR DELETE ON broadcast
EXECUTE PROCEDURE update_all_broadcast_sizes_trigger();

commit;

create table produces_movie
(
    production_company_id integer not null
        constraint produces_movie_production_company_id_fkey
            references production_company,
    movie_id              integer not null
        constraint produces_movie_movie_id_fkey
            references movie,
    constraint produces_movie_pkey
        primary key (production_company_id, movie_id)
);