DROP SCHEMA IF EXISTS projet CASCADE;
CREATE SCHEMA projet;

------------
---TABLES---
------------
---------CREATE TYPE state_furniture AS ENUM ('R','V','A','O','RE','VE'); 

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

CREATE TABLE projet.types (
	type_id SERIAL PRIMARY KEY,
	name VARCHAR(100) NOT NULL
);

CREATE TABLE projet.deliveries (
	delivery_id SERIAL PRIMARY KEY,
	estimated_delivery_date TIMESTAMP NOT NULL,
	is_delivered BOOLEAN DEFAULT FALSE,
	customer INTEGER REFERENCES projet.users(user_id) NOT NULL
);

CREATE TABLE projet.visits (
	visit_id SERIAL PRIMARY KEY,
	request_date TIMESTAMP NOT NULL,
	time_slot VARCHAR(100) NOT NULL,
	date_and_hours_visit TIMESTAMP NOT NULL,
	explanatory_note VARCHAR(100) NOT NULL,
	label_furniture VARCHAR(100) NOT NULL,
	users INTEGER REFERENCES projet.users(user_id) NOT NULL,
	address INTEGER REFERENCES projet.addresses(address_id) NOT NULL
);

CREATE TABLE projet.furniture (
	furniture_id SERIAL PRIMARY KEY,
	furniture_title VARCHAR(100) NOT NULL,
	purchase_price INTEGER NOT NULL,
	furniture_date_collection TIMESTAMP NULL,
	selling_price INTEGER NOT NULL,
	special_sale_price INTEGER NOT NULL,
	state_furniture state_furniture,
	deposit_date TIMESTAMP NULL,
	date_of_sale TIMESTAMP NULL,
	sale_withdrawal_date TIMESTAMP NULL,
	type INTEGER REFERENCES projet.types(type_id) NOT NULL,
	buyer INTEGER REFERENCES projet.users(user_id) NULL,
	seller INTEGER REFERENCES projet.users(user_id) NOT NULL
);

CREATE TABLE projet.photos (
	photo_id SERIAL PRIMARY KEY,
	pictures VARCHAR(100) NOT NULL,
	name VARCHAR(100) NULL
);

CREATE TABLE projet.photos_furniture (
	photo_id SERIAL PRIMARY KEY,
	is_visible BOOLEAN DEFAULT FALSE,
	is_favourite_photo BOOLEAN DEFAULT FALSE,
	photo INTEGER REFERENCES projet.photos(photo_id) NOT NULL,
	furniture INTEGER REFERENCES projet.furniture(furniture_id)NOT NULL
);

CREATE TABLE projet.photos_visits (
	visit INTEGER REFERENCES projet.visits(visit_id) NOT NULL,
	photo INTEGER REFERENCES projet.photos(photo_id) NOT NULL,
	PRIMARY KEY(visit,photo)
);

CREATE TABLE projet.options (
	option_id SERIAL PRIMARY KEY,
	option_term TIMESTAMP NOT NULL,
	beginning_option_date TIMESTAMP NOT NULL,
	customer INTEGER REFERENCES projet.users(user_id) NOT NULL,
	furniture INTEGER REFERENCES projet.furniture(furniture_id) NOT NULL
);


------------
---INSERT---
------------

INSERT INTO projet.addresses VALUES ('1', 'Avenue Louise' ,'93' ,'1000', 'Bruxelles','Belgique','1');
INSERT INTO projet.addresses VALUES ('2', 'Avenue Paul Hymans','20' ,'1200', 'Woluwe-Saint-Lambert','Belgique','1');

INSERT INTO projet.users VALUES ('1', 'Janssens', 'Olivier', 'Jo123' , '$2a$10$LnMTnzCT7c1HL2VtLAJzfurviQy70TTDlUg0wIHYGr/NV0LhW.QUq' , '1', 'jo@gmail.com', DEFAULT, DEFAULT, DEFAULT, '2019-10-10');
INSERT INTO projet.users VALUES ('2', 'Janssens', 'Axelle', 'Ja321' , '$2y$12$YFMeK.tR5EKqS5SIznRcLO795noc13r.PiiUmpz2mJ3JkF6GaOwba' , '1', 'ja@gmail.com', DEFAULT, DEFAULT, DEFAULT, '2019-11-11');
INSERT INTO projet.users VALUES ('3', 'Sow', 'Mathieu', 'Mathos' , '$2y$12$8G7MvnjHJ8EuvHosK74DDuNhNAMCwQKbxo8wCtmLQ3Tlfi4dd1Pge' , '2', 'soma@', DEFAULT, DEFAULT, DEFAULT, '2019-10-10');

SELECT user_id, username, first_name, last_name, address, email, is_boss,
            is_antique_dealer, is_confirmed, registration_date, password 
            FROM projet.users WHERE username = 'Jo123'