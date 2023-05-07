drop table if exists account;
create table account
(
    id      serial primary key,
    email   varchar(255) not null unique,
    balance numeric      not null
);

drop table if exists bankin_operation;
create table banking_operation
(
    id         serial primary key,
    account_id int references account,
    amount     numeric not null,
    date       timestamp
);
