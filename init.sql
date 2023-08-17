CREATE DATABASE IF NOT EXISTS ussd;
USE ussd;

    CREATE TABLE country (
                         id BIGINT NOT NULL AUTO_INCREMENT,
                         name VARCHAR(255) NOT NULL,
                         currency VARCHAR(3) NOT NULL,
                         create_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         last_modified_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                         status VARCHAR(255) NOT NULL,
                         PRIMARY KEY (ID)

                         )ENGINE=INNODB;

CREATE TABLE foreign_exchange_rate (
                         id BIGINT NOT NULL AUTO_INCREMENT,
                         source_currency VARCHAR(3) NOT NULL,
                         destination_currency VARCHAR(3) NOT NULL,
                         rate DECIMAL(10,2) NOT NULL,
                         create_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         last_modified_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                         status VARCHAR(255) NOT NULL,
                         PRIMARY KEY (ID),
                         INDEX (source_currency, destination_currency)
)ENGINE=INNODB;

CREATE TABLE prompt (
                         id BIGINT NOT NULL AUTO_INCREMENT,
                         name VARCHAR(255) NOT NULL,
                         message VARCHAR(1000) NOT NULL,
                         create_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         last_modified_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                         status VARCHAR(255) NOT NULL,
                         PRIMARY KEY (id)
)ENGINE=INNODB;

CREATE TABLE session (
                        id BIGINT NOT NULL AUTO_INCREMENT,
                        session_external_id VARCHAR(255) NOT NULL,
                        msisdn VARCHAR(15) NOT NULL,
                        prompt_response JSON DEFAULT NULL,
                        create_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        last_modified_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                        status VARCHAR(255) NOT NULL,
                        PRIMARY KEY (id),
                        INDEX (session_external_id, msisdn)
)ENGINE=INNODB;

CREATE TABLE transaction (
                        id BIGINT NOT NULL AUTO_INCREMENT,
                        session_id BIGINT NOT NULL,
                        source_msisdn VARCHAR(255) NOT NULL,
                        source_currency VARCHAR(3) NOT NULL,
                        destination_currency VARCHAR(3) NOT NULL,
                        amount DECIMAL(10,2) NOT NULL,
                        country VARCHAR(255) NOT NULL,
                        create_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        last_modified_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                        status VARCHAR(255) NOT NULL,
                        PRIMARY KEY (id),
                        FOREIGN KEY (session_id) REFERENCES session(id)
)ENGINE=INNODB;



INSERT INTO country (name, currency, status)
VALUES ('Kenya', 'KES', 'ACTIVE');

INSERT INTO country (name, currency, status)
VALUES ('Malawi', 'MWK', 'ACTIVE');

INSERT INTO foreign_exchange_rate (source_currency, destination_currency, rate, status)
VALUES ('ZAR', 'KES', 6.10, 'ACTIVE');

INSERT INTO foreign_exchange_rate (source_currency, destination_currency, rate, status)
VALUES ('ZAR', 'MWK', 42.50, 'ACTIVE');


INSERT INTO prompt (name, message, status)
VALUES ('country', 'Welcome to Mama Money!\nWhere would you like to send money today?\n1) Kenya\n2) Malawi', 'ACTIVE');

INSERT INTO prompt (name, message, status)
VALUES ('amount', 'How much money (in Rands)\nwould you like to send to \n<CountryName>?', 'ACTIVE');

INSERT INTO prompt (name, message, status)
VALUES ('confirmation', 'Your person you are sending to will receive: <Amount> <ForeignCurrencyCode>\n1) OK', 'ACTIVE');

INSERT INTO prompt (name, message, status)
VALUES ('end', 'Thank you for using Mama Money!', 'ACTIVE');

INSERT INTO prompt (name, message, status)
VALUES ('error', 'You have no active session with Mama Money,\nplease start new session', 'ACTIVE');