CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE SCHEMA IF NOT EXISTS app;
CREATE SCHEMA IF NOT EXISTS stat;

SET search_path TO app, public;

CREATE TABLE app.user_account (
                                  id BIGSERIAL PRIMARY KEY,
                                  external_id UUID NOT NULL,
                                  email VARCHAR(255) NOT NULL UNIQUE,
                                  name VARCHAR(255),
                                  created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                  modify_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                  version BIGINT NOT NULL,
                                  user_role VARCHAR(255),
                                  password VARCHAR(255)
);

CREATE TABLE app.bank_account (
                                  id BIGSERIAL PRIMARY KEY,
                                  external_id UUID NOT NULL,
                                  user_id BIGINT NOT NULL,
                                  created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                  modify_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                  account_version BIGINT NOT NULL,
                                  account_name VARCHAR(255) NOT NULL,
                                  account_number UUID NOT NULL,
                                  account_balance DECIMAL(19, 4),
                                  currency VARCHAR(255),
                                  FOREIGN KEY (user_id) REFERENCES app.user_account(id)
);

CREATE TABLE app.expense (
                             id BIGSERIAL PRIMARY KEY,
                             external_id UUID NOT NULL,
                             version INT NOT NULL,
                             created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                             modify_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                             expense_category VARCHAR(255),
                             expense_item VARCHAR(255),
                             expense_type VARCHAR(255),
                             expense DECIMAL(19, 4),
                             user_id BIGINT NOT NULL,
                             bank_account_id BIGINT,
                             FOREIGN KEY (user_id) REFERENCES app.user_account(id)
);

SET search_path TO stat, public;

CREATE TABLE stat.revenue (
                              id BIGSERIAL PRIMARY KEY,
                              user_id BIGINT NOT NULL,
                              date TIMESTAMP NOT NULL,
                              total_incomes DOUBLE PRECISION,
                              total_expenses DOUBLE PRECISION,
                              number_of_transactions BIGINT,
                              average_transaction_value DOUBLE PRECISION,
                              average_income_value DOUBLE PRECISION,
                              average_expense_value DOUBLE PRECISION
);

CREATE INDEX idx_revenue_date ON stat.revenue(date);