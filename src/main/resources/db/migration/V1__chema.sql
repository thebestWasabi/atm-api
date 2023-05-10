DROP TABLE IF EXISTS account;
CREATE TABLE account
(
    id      BIGSERIAL PRIMARY KEY,
    email   VARCHAR(255)   NOT NULL,
    balance NUMERIC(19, 2) NOT NULL
);

DROP TABLE IF EXISTS operation_list;
CREATE TABLE operation_list
(
    id               BIGSERIAL PRIMARY KEY,
    account_id       BIGINT         NOT NULL REFERENCES account (id),
    amount           NUMERIC(19, 2) NOT NULL,
    transaction_type VARCHAR(255)   NOT NULL,
    created_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
