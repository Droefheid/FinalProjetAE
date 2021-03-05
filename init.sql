DROP SCHEMA IF EXISTS projet CASCADE;
CREATE SCHEMA projet;

------------
---TABLES---
------------

CREATE TABLE projet.address (
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
	address INTEGER NOT NULL REFERENCES projet.address(address_id) NOT NULL,
	email VARCHAR(100),
	is_boss BOOLEAN NOT NULL,
	is_antique_dealer BOOLEAN NOT NULL,
	is_confirmed BOOLEAN NOT NULL,
	registration_date TIMESTAMP NOT NULL
);
