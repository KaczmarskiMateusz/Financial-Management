-- utworzenie schematów
CREATE EXTENSION IF NOT EXISTS pgcrypto;
CREATE SCHEMA IF NOT EXISTS app;
CREATE SCHEMA IF NOT EXISTS stat;

SET search_path TO app;

-- tabela user_account
CREATE TABLE IF NOT EXISTS user_account (
    id              BIGSERIAL     PRIMARY KEY,
    external_id     UUID          NOT NULL UNIQUE,
    email           VARCHAR(255)  NOT NULL UNIQUE,
    name            VARCHAR(255),
    created_on      TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modify_on       TIMESTAMP     DEFAULT CURRENT_TIMESTAMP,
    version         BIGINT        NOT NULL,
    user_role       VARCHAR(255)  NOT NULL,
    password        VARCHAR(255)  NOT NULL
);

-- tabela bank_account
CREATE TABLE IF NOT EXISTS bank_account (
    id               BIGSERIAL     PRIMARY KEY,
    external_id      UUID          NOT NULL UNIQUE,
    user_id          BIGINT        NOT NULL REFERENCES app.user_account(id),
    created_on       TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modify_on        TIMESTAMP     DEFAULT CURRENT_TIMESTAMP,
    account_version  BIGINT        NOT NULL,
    account_name     VARCHAR(255)  NOT NULL UNIQUE,
    account_balance  DECIMAL(19,4) NOT NULL,
    currency         VARCHAR(255)
);

-- tabela expense
CREATE TABLE IF NOT EXISTS expense (
    id               BIGSERIAL     PRIMARY KEY,
    external_id      UUID          NOT NULL,
    version          INT           NOT NULL,
    created_on       TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modify_on        TIMESTAMP     DEFAULT CURRENT_TIMESTAMP,
    expense_category VARCHAR(255),
    expense_item     VARCHAR(255),
    expense_type     VARCHAR(255),
    expense          DECIMAL(19,4),
    user_id          BIGINT        NOT NULL REFERENCES app.user_account(id),
    bank_account_id  BIGINT        REFERENCES app.bank_account(id)
);

-- teraz przełączamy się do schematu stat
SET search_path TO stat;

-- tabela stat.revenue
CREATE TABLE IF NOT EXISTS revenue (
    id                         BIGSERIAL     PRIMARY KEY,
    user_id                    BIGINT        NOT NULL,
    date                       TIMESTAMP     NOT NULL,
    total_incomes              DOUBLE PRECISION,
    total_expenses             DOUBLE PRECISION,
    number_of_transactions     BIGINT,
    average_transaction_value  DOUBLE PRECISION,
    average_income_value       DOUBLE PRECISION,
    average_expense_value      DOUBLE PRECISION
);

CREATE INDEX IF NOT EXISTS idx_revenue_date ON revenue(date);
