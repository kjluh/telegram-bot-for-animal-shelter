--liquibase formatted sql

--changeSet konstantin: 1
CREATE TABLE user_contact (
                       id int primary key,
                       chat_id bigint,
                       name text,
                       phone_number int
)