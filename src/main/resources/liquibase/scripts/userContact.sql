-- liquibase formatted sql

--changeSet konstantin:1
CREATE TABLE adoptive_parent (
                       id bigint generated by default as identity primary key,
                       chat_id bigint,
                       name text,
                       phone_number bigint,
                       address text,
                       message text,
                       trial_period DATE
);

--changeSet stepan:2
CREATE TABLE report (
                       id bigint generated by default as identity primary key,
                       parent_id bigint,
                       pet_id bigint,
                       photo_id text,
                       diet text,
                       health text,
                       behavior text,
                       report_date DATE
);

--changeSet anatoliy:3
CREATE TABLE pet (
                        id bigint generated by default as identity primary key,
                        adoptive_parent_id bigint,
                        type text,
                        name text,
                        age int,
                        description text
);
