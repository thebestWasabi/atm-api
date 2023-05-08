CREATE TABLE account
(
    id      BIGSERIAL PRIMARY KEY,
    email   VARCHAR(255)   NOT NULL,
    balance NUMERIC(19, 2) NOT NULL
);

CREATE TABLE transaction_type
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE transaction_history
(
    id                  BIGSERIAL PRIMARY KEY,
    account_id          BIGINT         NOT NULL,
    transaction_type_id BIGINT         NOT NULL,
    amount              NUMERIC(19, 2) NOT NULL,
    created_at          TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (account_id) REFERENCES account (id),
    FOREIGN KEY (transaction_type_id) REFERENCES transaction_type (id)
);

INSERT INTO transaction_type (name)
VALUES ('Пополнение');
INSERT INTO transaction_type (name)
VALUES ('Списание');
INSERT INTO transaction_type (name)
VALUES ('Перевод');

CREATE INDEX transaction_history_account_id_index ON transaction_history (account_id);
CREATE INDEX transaction_history_transaction_type_id_index ON transaction_history (transaction_type_id);
