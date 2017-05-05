create database omoggame;
create table user(
    id char(20) primary key,
    password char(20) not null,
	name char(20) not null,
    win int,
    lose int,
    draw int
);
