drop table if exists account;
create table account
(
    id     serial primary key,
    name   varchar(255) not null,
    amount numeric      not null
);

insert into account(name, amount)
values ('Maxim', 1000),
       ('Vadim', 1000),
       ('Vladimir', 1000),
       ('Daria', 1000);
