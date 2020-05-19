-- run me first
CREATE DATABASE credit_db;

-- the rest can be run at once
create table production_company (
    id serial not null
        constraint production_company_pk
            primary key,
    name varchar(250) unique not null
);

create table movie (
    id serial not null
        constraint movie_pk
            primary key,
    name varchar(250) not null,
    release_date date not null
);

create table produces_movie (
    production_company_id integer not null
        constraint produces_movie_production_company_id_fkey
            references production_company
            on delete cascade,
    movie_id integer not null
        constraint produces_movie_movie_id_fkey
            references movie
            on delete cascade,
    constraint produces_movie_pkey
        primary key (production_company_id, movie_id)
);

create table cast_members (
    id serial not null
        constraint cast_pk
            primary key,
    regdkid varchar(10) not null,
    name varchar(255) not null
);

create table movie_employs (
    movie_id integer not null
        constraint movie_employs_movie_id_fkey
            references movie
            on delete cascade,
    cast_id integer not null
        constraint movie_employs_cast_id_fkey
            references cast_members
            on delete cascade,
    role varchar(255) not null,
    constraint movie_employs_pkey
        primary key (movie_id, cast_id)
);

create table production (
    id serial not null
        constraint production_pk
            primary key,
    name varchar(255) not null,
    year date not null,
    number_of_seasons integer default 0,
    number_of_episodes integer default 0
);

create table broadcast (
    id serial not null
        constraint broadcast_pk
            primary key,
    name varchar(250) not null,
    air_date date not null,
    episode_number integer not null,
    season_number integer not null
);

create table broadcast_employs (
    broadcast_id integer not null
        constraint broadcast_employs_broadcast_id_fkey
            references broadcast
            on delete cascade,
    cast_id integer not null
        constraint broadcast_employs_cast_id_fkey
            references cast_members
            on delete cascade,
    role varchar(255) not null,
    constraint broadcast_employs_pkey
        primary key (broadcast_id, cast_id)
);

create table produces (
    production_company_id integer not null
        constraint produces_production_company_id_fkey
            references production_company
            on delete cascade,
    production_id integer not null
        constraint produces_production_id_fkey
            references production
            on delete cascade,
    constraint produces_pkey
        primary key (production_company_id, production_id)
);

create table contains (
    production_id integer not null
        constraint contains_production_id_fkey
            references production
            on delete cascade,
    broadcast_id integer not null
        constraint contains_broadcast_id_fkey
            references broadcast
            on delete cascade,
    constraint contains_pkey
        primary key (production_id, broadcast_id)
);

-- update single broadcast season number
CREATE OR REPLACE PROCEDURE update_season_number(production_id_variable integer)
AS $$
DECLARE
    number_of_seasons_temp integer := 1;
BEGIN
    SELECT coalesce(max(broadcast.season_number), 0) INTO number_of_seasons_temp
    FROM broadcast, produces, contains
        where broadcast.id = contains.broadcast_id
        and contains.production_id = produces.production_id
        and produces.production_id = production_id_variable;
    UPDATE production SET number_of_seasons = number_of_seasons_temp WHERE id = production_id_variable;
END; $$
    LANGUAGE plpgsql;

-- update single broadcast episode number
CREATE OR REPLACE PROCEDURE update_episode_number(production_id_variable integer)
AS $$
DECLARE
    number_of_episodes_temp integer := 1;
BEGIN
    SELECT count(broadcast.episode_number) INTO number_of_episodes_temp FROM broadcast, produces, contains
        where broadcast.id = contains.broadcast_id
        and contains.production_id = produces.production_id
        and produces.production_id = production_id_variable;
    UPDATE production SET number_of_episodes = number_of_episodes_temp WHERE id = production_id_variable;
END; $$
    LANGUAGE plpgsql;

-- update all broadcasts procedure
CREATE OR REPLACE PROCEDURE update_all_broadcast_sizes()
AS $$
DECLARE
    productions CURSOR FOR SELECT DISTINCT(id) AS id FROM production;
BEGIN
    FOR production in productions LOOP
            CALL update_season_number(production.id);
            CALL update_episode_number(production.id);
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
    AFTER INSERT OR DELETE OR UPDATE ON contains
EXECUTE PROCEDURE update_all_broadcast_sizes_trigger();


-- Inserts to have baseline data. Is used for the tests.
-- Insert order is important.
INSERT INTO production_company VALUES (1, 'Warner Bros.');
INSERT INTO movie VALUES (1, 'Harry Potter and the Sorcerer''s Stone', date'2001-01-01');
INSERT INTO produces_movie VALUES (1, 1);
INSERT INTO cast_members VALUES (1, 'daniel1999', 'Daniel Radcliffe');
INSERT INTO cast_members VALUES (2, 'tomell2000', 'Tom Ellis');
INSERT INTO movie_employs VALUES (1, 1, 'Harry Potter');
INSERT INTO production VALUES (1, 'Lucifer', date'2015-01-01');
INSERT INTO produces VALUES (1, 1);
INSERT INTO broadcast VALUES (1, 'Pilot', date'2016-01-25', 1, 1);
INSERT INTO broadcast VALUES (2, 'Lucifer, Stay. Good Devil', date'2016-02-01', 2, 1);
INSERT INTO broadcast_employs VALUES (1, 2, 'Lucifer');
INSERT INTO broadcast_employs VALUES (2, 2, 'Lucifer');
INSERT INTO contains VALUES (1, 1);
INSERT INTO contains VALUES (1, 2);

SELECT setval('movie_id_seq', max(id)) FROM movie;
SELECT setval('broadcast_id_seq', max(id)) FROM broadcast;
SELECT setval('cast_members_id_seq', max(id)) FROM cast_members;
SELECT setval('production_id_seq', max(id)) FROM production;
SELECT setval('production_company_id_seq', max(id)) FROM production_company;