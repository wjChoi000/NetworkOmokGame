create database omoggame;
create table user(
    id char(20) CHARACTER SET utf8 primary key,
    password char(20) CHARACTER SET utf8 not null ,
	name char(20) CHARACTER SET utf8 not null ,
    win int,
    lose int,
    draw int
) CHARACTER SET utf8;
