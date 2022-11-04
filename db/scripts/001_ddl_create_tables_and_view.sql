create table account
(
    id         serial primary key,
    first_name varchar(100),
    last_name  varchar(100)
);

create table card
(
    account_id  integer primary key references account (id),
    card_number integer,
    pin         integer
);

create table balance
(
    user_balance numeric,
    card_number  integer references card (card_number)
);

create table cash_machine
(
    id            serial primary key,
    fifties       integer,
    hundreds      integer,
    five_hundreds integer,
    thousands     integer,
    dol_tens      integer,
    dol_twenties  integer,
    dol_fifties   integer,
    dol_hundreds  integer
);

CREATE VIEW money_inside AS
SELECT (fifties * 50 + hundreds * 100 + five_hundreds * 500 + thousands * 1000
    + dol_tens * 600 + dol_twenties * 1200 + dol_fifties * 3000 + dol_hundreds * 6000)
           AS sum_money
FROM cash_machine;