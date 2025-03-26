CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE SCHEMA IF NOT EXISTS app;

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
                              account_balance DECIMAL(19, 4),
                              currency VARCHAR(255),
                              FOREIGN KEY (user_id) REFERENCES user_account(id)
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
                         FOREIGN KEY (user_id) REFERENCES user_account(id)
);


INSERT INTO user_account (external_id, email, name, version, user_role, password)
VALUES
    (gen_random_uuid(), 'admin@financialapp.com', 'admin', 1, 'ADMIN', '$2a$12$YsXuaGHGUoXSdwsMtzenY.p2A5bB.chYlBYcdCQ..QWXlKOOOMqHG'),
    (gen_random_uuid(), 'demo@financialapp.com', 'demo', 1, 'USER', '$2a$10$l4Pn4N2pQNVGOrpdU0WxYOrQFhOCw7a5NeDX.vlJj0z2MY91txCry'),
    (gen_random_uuid(), 'user@financialapp.com', 'user', 1, 'USER', '$2a$10$NGV2B/i/ZoS4EcRdi4bqC.7hiEGe0UELAtmg5UhwBGccOyGwLPiVu'),
    (gen_random_uuid(), 'revenue@financialapp.com', 'Revenue User', 1, 'USER', '$2a$12$ExampleHashedPasswordForRevenueUser');

-- Wstawianie kont bankowych dla użytkowników
INSERT INTO bank_account (external_id, user_id, account_version, account_name, account_balance, currency)
VALUES
    (gen_random_uuid(), (SELECT id FROM user_account WHERE email = 'admin@financialapp.com'), 1, 'Admin Account', 1000.00, 'USD'),
    (gen_random_uuid(), (SELECT id FROM user_account WHERE email = 'demo@financialapp.com'), 1, 'Demo Bank Account', 500.00, 'PLN'),
    (gen_random_uuid(), (SELECT id FROM user_account WHERE email = 'user@financialapp.com'), 1, 'User Bank Account USD', 500.00, 'USD'),
    (gen_random_uuid(), (SELECT id FROM user_account WHERE email = 'user@financialapp.com'), 1, 'User Bank Account PLN', 10000, 'PLN'),
    (gen_random_uuid(), (SELECT id FROM user_account WHERE email = 'revenue@financialapp.com'), 1, 'Revenue Bank Account', 750.00, 'USD');

-- Wstawianie rekordów do tabeli expense dla różnych użytkowników

-- Dla użytkownika "admin"
INSERT INTO expense (external_id, version, created_on, modify_on, expense_item, expense_category, expense_type, expense, user_id)
VALUES
    (gen_random_uuid(), 1, CURRENT_TIMESTAMP - INTERVAL '10 minutes', CURRENT_TIMESTAMP - INTERVAL '10 minutes', 'Admin Office Supplies', 'OFFICE', 'EXPENSE', 120.00, (SELECT id FROM user_account WHERE email = 'admin@financialapp.com')),
    (gen_random_uuid(), 1, CURRENT_TIMESTAMP - INTERVAL '5 minutes', CURRENT_TIMESTAMP - INTERVAL '5 minutes', 'Admin Travel', 'TRAVEL', 'EXPENSE', 250.00, (SELECT id FROM user_account WHERE email = 'admin@financialapp.com'));

-- Dla użytkownika "demo"
INSERT INTO expense (external_id, version, created_on, modify_on, expense_item, expense_category, expense_type, expense, user_id)
VALUES
    (gen_random_uuid(), 1, CURRENT_TIMESTAMP - INTERVAL '12 minutes', CURRENT_TIMESTAMP - INTERVAL '12 minutes', 'Demo Software License', 'SOFTWARE', 'EXPENSE', 50.00, (SELECT id FROM user_account WHERE email = 'demo@financialapp.com')),
    (gen_random_uuid(), 1, CURRENT_TIMESTAMP - INTERVAL '8 minutes', CURRENT_TIMESTAMP - INTERVAL '8 minutes', 'Demo Subscription', 'SUBSCRIPTION', 'EXPENSE', 30.00, (SELECT id FROM user_account WHERE email = 'demo@financialapp.com')),
    (gen_random_uuid(), 1, CURRENT_TIMESTAMP - INTERVAL '3 minutes', CURRENT_TIMESTAMP - INTERVAL '3 minutes', 'Demo Income', 'OTHER', 'INCOME', 100.00, (SELECT id FROM user_account WHERE email = 'demo@financialapp.com'));

-- Dla użytkownika "user"
INSERT INTO expense (external_id, version, created_on, modify_on, expense_item, expense_category, expense_type, expense, user_id)
VALUES
    (gen_random_uuid(), 1, CURRENT_TIMESTAMP - INTERVAL '20 minutes', CURRENT_TIMESTAMP - INTERVAL '20 minutes', 'User Home Expense', 'HOME', 'EXPENSE', 15.50, (SELECT id FROM user_account WHERE email = 'user@financialapp.com')),
    (gen_random_uuid(), 1, CURRENT_TIMESTAMP - INTERVAL '18 minutes', CURRENT_TIMESTAMP - INTERVAL '18 minutes', 'User Work Expense', 'WORK', 'EXPENSE', 2.80, (SELECT id FROM user_account WHERE email = 'user@financialapp.com')),
    (gen_random_uuid(), 1, CURRENT_TIMESTAMP - INTERVAL '17 minutes', CURRENT_TIMESTAMP - INTERVAL '17 minutes', 'User Other Expense', 'OTHER', 'EXPENSE', 10.00, (SELECT id FROM user_account WHERE email = 'user@financialapp.com')),
    (gen_random_uuid(), 1, CURRENT_TIMESTAMP - INTERVAL '15 minutes', CURRENT_TIMESTAMP - INTERVAL '15 minutes', 'User Sports Equipment', 'SPORT', 'EXPENSE', 50.00, (SELECT id FROM user_account WHERE email = 'user@financialapp.com')),
    (gen_random_uuid(), 1, CURRENT_TIMESTAMP - INTERVAL '14 minutes', CURRENT_TIMESTAMP - INTERVAL '14 minutes', 'User Food', 'FOOD', 'EXPENSE', 20.00, (SELECT id FROM user_account WHERE email = 'user@financialapp.com')),
    (gen_random_uuid(), 1, CURRENT_TIMESTAMP - INTERVAL '13 minutes', CURRENT_TIMESTAMP - INTERVAL '13 minutes', 'User Income Other', 'OTHER', 'INCOME', 20.00, (SELECT id FROM user_account WHERE email = 'user@financialapp.com'));

-- Dla nowego użytkownika "revenue"
INSERT INTO expense (external_id, version, created_on, modify_on, expense_item, expense_category, expense_type, expense, user_id)
VALUES
    (gen_random_uuid(), 1, CURRENT_TIMESTAMP - INTERVAL '25 minutes', CURRENT_TIMESTAMP - INTERVAL '25 minutes', 'Revenue Office Rent', 'OFFICE', 'EXPENSE', 300.00, (SELECT id FROM user_account WHERE email = 'revenue@financialapp.com')),
    (gen_random_uuid(), 1, CURRENT_TIMESTAMP - INTERVAL '23 minutes', CURRENT_TIMESTAMP - INTERVAL '23 minutes', 'Revenue Consulting Income', 'INCOME', 'INCOME', 500.00, (SELECT id FROM user_account WHERE email = 'revenue@financialapp.com')),
    (gen_random_uuid(), 1, CURRENT_TIMESTAMP - INTERVAL '21 minutes', CURRENT_TIMESTAMP - INTERVAL '21 minutes', 'Revenue Travel Expense', 'TRAVEL', 'EXPENSE', 150.00, (SELECT id FROM user_account WHERE email = 'revenue@financialapp.com')),
    (gen_random_uuid(), 1, CURRENT_TIMESTAMP - INTERVAL '20 minutes', CURRENT_TIMESTAMP - INTERVAL '20 minutes', 'Revenue Equipment Purchase', 'EQUIPMENT', 'EXPENSE', 200.00, (SELECT id FROM user_account WHERE email = 'revenue@financialapp.com')),
    (gen_random_uuid(), 1, CURRENT_TIMESTAMP - INTERVAL '19 minutes', CURRENT_TIMESTAMP - INTERVAL '19 minutes', 'Revenue Software Income', 'SOFTWARE', 'INCOME', 250.00, (SELECT id FROM user_account WHERE email = 'revenue@financialapp.com')),
    (gen_random_uuid(), 1, CURRENT_TIMESTAMP - INTERVAL '18 minutes', CURRENT_TIMESTAMP - INTERVAL '18 minutes', 'Revenue Misc Expense', 'MISC', 'EXPENSE', 100.00, (SELECT id FROM user_account WHERE email = 'revenue@financialapp.com'));