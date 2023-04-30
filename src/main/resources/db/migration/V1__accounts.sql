drop table if exists account;
create table account
(
    id     serial primary key,
    name   varchar(255) not null,
    balance numeric      not null
);

insert into account(name, balance)
values ('Maxim', 1000),
       ('Vadim', 1000),
       ('Vladimir', 1000),
       ('Daria', 1000);


drop table if exists banking_operation;
create table banking_operation
(
    id         serial primary key,
    account_id int references account,
    amount     numeric not null,
    date date
);

