DROP DATABASE if exists YinmAssignment4;

CREATE DATABASE YinmAssignment4;

USE YinmAssignment4;

CREATE TABLE User (
	userid varchar(50) primary key not null,
    pwd varchar(50) not null
);
