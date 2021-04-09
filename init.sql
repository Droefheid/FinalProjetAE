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
	date_and_hours_visit TIMESTAMP NULL,
	explanatory_note VARCHAR(100) NULL,
	label_furniture VARCHAR(100) NOT NULL,
	is_confirmed BOOLEAN DEFAULT FALSE,
	users INTEGER REFERENCES projet.users(user_id) NOT NULL,
	address INTEGER REFERENCES projet.addresses(address_id) NOT NULL
);

CREATE TABLE projet.furnitures (
	furniture_id SERIAL PRIMARY KEY,
	furniture_title VARCHAR(100) NOT NULL,
	purchase_price FLOAT NOT NULL,
	furniture_date_collection TIMESTAMP NULL,
	selling_price FLOAT NOT NULL,
	special_sale_price FLOAT NOT NULL,
	state_furniture state_furniture,
	deposit_date TIMESTAMP NULL,
	date_of_sale TIMESTAMP NULL,
	sale_withdrawal_date TIMESTAMP NULL,
	delivery INTEGER REFERENCES projet.deliveries(delivery_id) NULL,
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
	is_visible BOOLEAN DEFAULT FALSE,
	is_favourite_photo BOOLEAN DEFAULT FALSE,
	photo_id INTEGER REFERENCES projet.photos(photo_id) NOT NULL,
	furniture INTEGER REFERENCES projet.furnitures(furniture_id)NOT NULL,
	PRIMARY KEY(photo_id)
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
	furniture INTEGER REFERENCES projet.furnitures(furniture_id) NOT NULL,
	pick_up_date TIMESTAMP NOT NULL
);

/*
INSERT INTO projet.types(
	type_id, name)
	VALUES (DEFAULT, 'Chenes');
INSERT INTO projet.addresses(
	address_id, street, building_number, postcode, commune, country, unit_number)
	VALUES (DEFAULT, 'test', '10', '1120', '1', 'Belgique', '12');
INSERT INTO projet.users(
	user_id, last_name, first_name, username, password, address, email, is_boss, is_antique_dealer, is_confirmed, registration_date)
	VALUES (DEFAULT, 'Bryan', 'VM', 'Bracouz', 'azerty', 1, 'test@test.com', FALSE, FALSE, FALSE, '1970-01-01 00:00:01');
INSERT INTO projet.furnitures(
	furniture_id, furniture_title, purchase_price, furniture_date_collection, selling_price, special_sale_price, state_furniture, deposit_date, date_of_sale, sale_withdrawal_date, delivery, type, buyer, seller)
	VALUES (DEFAULT, 'Title furniture 4', 0, NULL, 0, 0, 'V', NULL, NULL, NULL, NULL, 1, NULL,2 );
*/

