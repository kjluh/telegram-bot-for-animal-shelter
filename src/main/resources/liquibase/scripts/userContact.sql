--liquibase formatted sql

--changeSet konstantin: 1
CREATE TABLE user_contact (
                       id generated by default as identity primary key,
                       chat_id bigint,
                       name text,
                       phone_number int
)