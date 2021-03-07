DROP SCHEMA IF EXISTS projet CASCADE;
CREATE SCHEMA projet;

------------
---TABLES---
------------

CREATE TABLE projet.addresses (
	address_id SERIAL PRIMARY KEY,
	street VARCHAR(100) NOT NULL,
	building_number VARCHAR(10) NOT NULL,
	postcode VARCHAR(10) NOT NULL,
	commune VARCHAR(100) NOT NULL,
	country VARCHAR(100) NOT NULL,
	unit_number VARCHAR(10) NOT NULL
);



CREATE TABLE projet.users (
	user_id SERIAL PRIMARY KEY,
	last_name VARCHAR(100) NOT NULL,
	first_name VARCHAR(100) NOT NULL,
	username VARCHAR(100) NOT NULL,
	password VARCHAR(100) NOT NULL,
	address INTEGER REFERENCES projet.addresses(address_id) NOT NULL,
	email VARCHAR(100) NOT NULL,
	is_boss BOOLEAN DEFAULT FALSE,
	is_antique_dealer BOOLEAN DEFAULT FALSE,
	is_confirmed BOOLEAN DEFAULT FALSE,
	registration_date TIMESTAMP NOT NULL
);

------------
---INSERT---
------------

INSERT INTO projet.addresses VALUES ('1', 'Avenue Louise' ,'93' ,'1000', 'Bruxelles','Belgique','1');
INSERT INTO projet.addresses VALUES ('2', 'Avenue Paul Hymans','20' ,'1200', 'Woluwe-Saint-Lambert','Belgique','1');

INSERT INTO projet.users VALUES ('1', 'Janssens', 'Olivier', 'Jo123' , '$2y$12$jOweCgWXfokomtxHtkxJ9OZRRTf/Znph10DwlPSXL1wyjz49N2sra' , '1', 'jo@gmail.com', DEFAULT, DEFAULT, DEFAULT, '2019-10-10');
INSERT INTO projet.users VALUES ('2', 'Janssens', 'Axelle', 'Ja321' , '$2y$12$YFMeK.tR5EKqS5SIznRcLO795noc13r.PiiUmpz2mJ3JkF6GaOwba' , '1', 'ja@gmail.com', DEFAULT, DEFAULT, DEFAULT, '2019-11-11');
INSERT INTO projet.users VALUES ('3', 'Sow', 'Mathieu', 'Mathos' , '$2y$12$8G7MvnjHJ8EuvHosK74DDuNhNAMCwQKbxo8wCtmLQ3Tlfi4dd1Pge' , '2', 'soma@', DEFAULT, DEFAULT, DEFAULT, '2019-10-10');