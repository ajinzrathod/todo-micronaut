--liquibase formatted sql

--changeset ajinkya:1
create table todos (id bigint not null auto_increment, status integer, title varchar(255), primary key (id))
