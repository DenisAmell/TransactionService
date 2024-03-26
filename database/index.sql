CREATE TABLE currency
(
    id            BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    currency_name VARCHAR(255),
    code          BIGINT,
    CONSTRAINT pk_currency PRIMARY KEY (id)
);

DROP TABLE courses;

CREATE TABLE courses
(
    id          BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    date        DATE,
    currency_id BIGINT,
    coefficient DECIMAL,
    CONSTRAINT pk_courses PRIMARY KEY (id)
);

drop table category;

CREATE TABLE category
(
    id            BIGINT,
    name_category VARCHAR(255),
    CONSTRAINT pk_category_id PRIMARY KEY (id)
);

CREATE TABLE limits
(
    id          BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    user_id     BIGINT,
    sum_limit   DOUBLE PRECISION,
    category_id BIGINT,
    CONSTRAINT fk_category_id FOREIGN KEY (category_id) REFERENCES category (id),
    CONSTRAINT pk_limits PRIMARY KEY (id)
);

CREATE TABLE transaction
(
    id                    BIGINT,
    user_id               BIGINT,
    account_from          BIGINT,
    account_to            BIGINT,
    currency_shortname_id BIGINT,
    sum_transaction       money,
    expense_category_id   BIGINT,
    transaction_date_time TIMESTAMP,
    limits_exceeded       BOOLEAN,
    CONSTRAINT pk_transaction PRIMARY KEY (id, user_id, account_to),
    CONSTRAINT fk_transaction_currency FOREIGN KEY (currency_shortname_id) REFERENCES currency (id),
    CONSTRAINT fk_transaction_category FOREIGN KEY (expense_category_id) REFERENCES category (id)
);

ALTER TABLE courses
    ADD CONSTRAINT fk_currency_id FOREIGN KEY (currency_id) REFERENCES currency (id) ON UPDATE CASCADE;


insert into currency
VALUES (1, 123457, 'RUB');
insert into currency
VALUES (2, 789010, 'USD');

insert into category
values (1, 'product');
insert into category
values (2, 'service');

select *
from currency;

select *
from courses;

delete
from courses;

select *
from transaction;

select t.user_id,
       t.transaction_date_time,
       t.sum_transaction,
       t.limits_exceeded,
       l.sum_limit,
       l.date,
       l.fk_category_id
from limits l
         join(select transaction.limits_exceeded,
                     transaction.sum_transaction,
                     transaction.user_id,
                     transaction.transaction_date_time
              from transaction
              where transaction.limits_exceeded = TRUE) t ON t.sum_transaction > l.sum_limit
group by t.user_id, t.transaction_date_time, l.sum_limit, l.date, l.fk_category_id, t.sum_transaction,
         t.limits_exceeded;





