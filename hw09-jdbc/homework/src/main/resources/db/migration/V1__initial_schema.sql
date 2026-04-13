create table test
(
    id   int,
    name varchar(50)
);
create table client
(
    id   bigserial not null primary key,
    name varchar(50)
);
create table manager
(
    id     bigserial not null primary key,
    label  varchar(255),
    param1 varchar(255)
);