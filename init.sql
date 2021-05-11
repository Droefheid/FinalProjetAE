DROP SCHEMA IF EXISTS projet CASCADE;
CREATE SCHEMA projet;

------------
---TABLES---
------------
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
	state_furniture VARCHAR(2) NOT NULL,
	deposit_date TIMESTAMP NULL,
	date_of_sale TIMESTAMP NULL,
	sale_withdrawal_date TIMESTAMP NULL,
	delivery INTEGER REFERENCES projet.deliveries(delivery_id) NULL,
	type INTEGER REFERENCES projet.types(type_id) NOT NULL,
	buyer INTEGER REFERENCES projet.users(user_id) NULL,
	seller INTEGER REFERENCES projet.users(user_id) NOT NULL,
	pick_up_date TIMESTAMP NOT NULL 
);


CREATE TABLE projet.photos (
	photo_id SERIAL PRIMARY KEY,
	pictures VARCHAR NOT NULL,
	name VARCHAR(100) NULL
);

CREATE TABLE projet.photos_furniture (
	is_visible BOOLEAN DEFAULT FALSE,
	is_favourite_photo BOOLEAN DEFAULT FALSE,
	photo_id INTEGER REFERENCES projet.photos(photo_id) NOT NULL,
	furniture INTEGER REFERENCES projet.furnitures(furniture_id) NOT NULL,
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
	is_currently_reserved BOOLEAN DEFAULT FALSE,
	customer INTEGER REFERENCES projet.users(user_id) NOT NULL,
	furniture INTEGER REFERENCES projet.furnitures(furniture_id) NOT NULL
);


--------------------------
---INSERT USERS+ADDRESS---
--------------------------
--------------------------

------ADMIN BERT----------

INSERT INTO projet.addresses(
	address_id, street, building_number, postcode, commune, country, unit_number)
	VALUES (DEFAULT, 'sente des artistes', '1bis', '4800', 'Verviers', 'Belgique', '0');

INSERT INTO projet.users(
	user_id, last_name, first_name, username, password, address, email, is_boss, is_antique_dealer, is_confirmed, registration_date)
	VALUES (DEFAULT, 'Satcho', 'Albert', 'bert', '$2a$10$g3anrXSvu7tEhm3l.Ychru4SKdxJt8OG047jQwSyTxVhtllZSUYVC', 1, 'bert.satcho@gmail.be', TRUE, TRUE, TRUE, '2021-03-22 00:00:01');


-------ADMIN LAU---------

INSERT INTO projet.addresses(
	address_id, street, building_number, postcode, commune, country, unit_number)
	VALUES (DEFAULT, 'sente des artistes', '18', '4800', 'Verviers', 'Belgique', '0');

INSERT INTO projet.users(
	user_id, last_name, first_name, username, password, address, email, is_boss, is_antique_dealer, is_confirmed, registration_date)
	VALUES (DEFAULT, 'Satcho', 'Laurent', 'lau', '$2a$10$5jwdjrDBIfn5Neg9FGBA1u7YH6/AP798GRxlWC6CfWNXH14zG/zny', 2, 'laurent.satcho@gmail.be', TRUE, TRUE, TRUE, '2021-03-22 00:00:01');


----------USERS CAROLINE---------

INSERT INTO projet.addresses(
	address_id, street, building_number, postcode, commune, country, unit_number)
	VALUES (DEFAULT, 'rue de lEglise', '11', '4987', 'Stoumont', 'Belgique', 'B1');

INSERT INTO projet.users(
	user_id, last_name, first_name, username, password, address, email, is_boss, is_antique_dealer, is_confirmed, registration_date)
	VALUES (DEFAULT, 'Line', 'Caroline', 'Caro', '$2a$10$u6rA4nMLQ6lOeYMU1F7IDe6S2AkRVKjpBFhpVJ7aUTfRR1PU9PfjS', 3, 'caro.line@hotmail.com', FALSE, TRUE, TRUE, '2021-03-23 00:00:01');


----------USERS ACHILLE---------

INSERT INTO projet.addresses(
	address_id, street, building_number, postcode, commune, country, unit_number)
	VALUES (DEFAULT, 'rue de renkin', '7', '4800', 'Verviers', 'Belgique', '0');

INSERT INTO projet.users(
	user_id, last_name, first_name, username, password, address, email, is_boss, is_antique_dealer, is_confirmed, registration_date)
	VALUES (DEFAULT, 'Ile', 'Achille', 'achil', '$2a$10$GsWydc3I8.V8T1lSwK2UD.cCoDu5jHcZbDzGA9lD4ZOCHS.lAIT7W', 4, 'ach.ile@gmail.com', FALSE, FALSE, TRUE, '2021-03-23 00:00:01');


----------USERS BASILE---------

INSERT INTO projet.addresses(
	address_id, street, building_number, postcode, commune, country, unit_number)
	VALUES (DEFAULT, 'Lammerskreuzstraße ** ', '6', '52159', 'Roetgen', 'Allemagne', '0');

INSERT INTO projet.users(
	user_id, last_name, first_name, username, password, address, email, is_boss, is_antique_dealer, is_confirmed, registration_date)
	VALUES (DEFAULT, 'Ile', 'Basile', 'bazz', '$2a$10$2YdZqX3rK03Y4xNjSHWieuHfjsaf.qJ5Uf53Q0g/rq.bd2eCPkSfS', 5, 'bas.ile@gmail.com', FALSE, FALSE, TRUE, '2021-03-23 00:00:01');


----------USERS THEOPHILE---------

INSERT INTO projet.users(
	user_id, last_name, first_name, username, password, address, email, is_boss, is_antique_dealer, is_confirmed, registration_date)
	VALUES (DEFAULT, 'Ile', 'Theophile', 'theo', '$2a$10$4JHq0Pqj0S1MIBQ4U.2B.e3we47d2smcGDxDn.9FMB99Sqbph4fye', 4, 'theo.phile@proximus.be', FALSE, TRUE, TRUE, '2021-03-30 00:00:01');


----------USERS CHARLINE---------

INSERT INTO projet.addresses(
	address_id, street, building_number, postcode, commune, country, unit_number)
	VALUES (DEFAULT, 'Rue des Minières', '45', '4800', 'Verviers', 'Allemagne', 'Ter');

INSERT INTO projet.users(
	user_id, last_name, first_name, username, password, address, email, is_boss, is_antique_dealer, is_confirmed, registration_date)
	VALUES (DEFAULT, 'Ile', 'Basile', 'charline', '$2a$10$XHpL237HNmM09IPxBpJcNeuxpdcKwj2mIlSesx7UGgnSci7XPMhS2', 6, 'charline@proximus.be', FALSE, FALSE, TRUE, '2021-04-22 00:00:01');


-------------------
---INSERT VISITS---
-------------------
-------------------

INSERT INTO projet.addresses(
	address_id, street, building_number, postcode, commune, country, unit_number)
	VALUES (DEFAULT, 'Rue Victor Bouillenne', '9bte', '4800', 'Verviers', 'Belgique', '4C');

INSERT INTO projet.visits(
	visit_id, request_date, time_slot, date_and_hours_visit, explanatory_note, label_furniture, is_confirmed, users, address)
	VALUES (DEFAULT, '2021-03-24 00:00:01', 'lundi de 18h à 22h', '2021-03-29 20:00:00', NULL, 'Bahut et Bureau', TRUE, 4, 4);

INSERT INTO projet.visits(
	visit_id, request_date, time_slot, date_and_hours_visit, explanatory_note, label_furniture, is_confirmed, users, address)
	VALUES (DEFAULT, '2021-03-25 00:00:01', 'lundi de 18h à 22h', NULL, 'Meuble trop récent', 'Table', FALSE, 4, 4);

INSERT INTO projet.visits(
	visit_id, request_date, time_slot, date_and_hours_visit, explanatory_note, label_furniture, is_confirmed, users, address)
	VALUES (DEFAULT, '2021-03-25 00:00:01', 'tous les jours de 15h à 18h', '2021-03-29 15:00:00', NULL, 'Table et Secrétaire', TRUE, 5, 5);

INSERT INTO projet.visits(
	visit_id, request_date, time_slot, date_and_hours_visit, explanatory_note, label_furniture, is_confirmed, users, address)
	VALUES (DEFAULT, '2021-04-21 00:00:01', 'tous les matins de 9h à 13h', NULL, NULL, 'Lit', FALSE, 6, 7);

INSERT INTO projet.visits(
	visit_id, request_date, time_slot, date_and_hours_visit, explanatory_note, label_furniture, is_confirmed, users, address)
	VALUES (DEFAULT, '2021-04-22 00:00:01', 'tous les jours de 16h à 19h', '2021-04-26 18:00:00', NULL, '3 Bureau et 2 Coiffeuse', TRUE, 3, 3);


------------------------
---INSERT PHOTO---------
------------------------
------------------------

INSERT INTO projet.photos(
	photo_id, pictures, name)
	VALUES (DEFAULT, '/src/main/resources/photos/Bahut_2.jpg', 'Bahut_2.jpg');

INSERT INTO projet.photos(
	photo_id, pictures, name)
	VALUES (DEFAULT, '/src/main/resources/photos/Bureau_1.jpg', 'Bureau_1.jpg');

INSERT INTO projet.photos(
	photo_id, pictures, name)
	VALUES (DEFAULT, '/src/main/resources/photos/Bureau_1-Visible-PrÃ©fÃ©rÃ©e.jpg', 'Bureau_1-Visible-PrÃ©fÃ©rÃ©e.jpg');

INSERT INTO projet.photos(
	photo_id, pictures, name)
	VALUES (DEFAULT, '/src/main/resources/photos/table-jardin-recente.jpg', 'table-jardin-recente.jpg');

INSERT INTO projet.photos(
	photo_id, pictures, name)
	VALUES (DEFAULT, '/src/main/resources/photos/Table.jpg', 'Table.jpg');

INSERT INTO projet.photos(
	photo_id, pictures, name)
	VALUES (DEFAULT, '/src/main/resources/photos/Secretaire.jpg', 'Secretaire.jpg');

INSERT INTO projet.photos(
	photo_id, pictures, name)
	VALUES (DEFAULT, '/src/main/resources/photos/Lit_LitBaldaquin.jpg', 'Lit_LitBaldaquin.jpg');

INSERT INTO projet.photos(
	photo_id, pictures, name)
	VALUES (DEFAULT, '/src/main/resources/photos/Bureau_2.jpg', 'Bureau_2.jpg');

INSERT INTO projet.photos(
	photo_id, pictures, name)
	VALUES (DEFAULT, '/src/main/resources/photos/Bureau-3_ImageClient.jpg', 'Bureau-3_ImageClient.jpg');

INSERT INTO projet.photos(
	photo_id, pictures, name)
	VALUES (DEFAULT, '/src/main/resources/photos/Bureau-3-Visible.jpg', 'Bureau-3-Visible.jpg');

INSERT INTO projet.photos(
	photo_id, pictures, name)
	VALUES (DEFAULT, '/src/main/resources/photos/Bureau-3-Visible-PrÃ©fÃ©rÃ©e.jpg', 'Bureau-3-Visible-PrÃ©fÃ©rÃ©e.jpg');

INSERT INTO projet.photos(
	photo_id, pictures, name)
	VALUES (DEFAULT, '/src/main/resources/photos/Bureau-8.jpg', 'Bureau-8.jpg');

INSERT INTO projet.photos(
	photo_id, pictures, name)
	VALUES (DEFAULT, '/src/main/resources/photos/Bureau-8-Visible-PrÃ©fÃ©rÃ©e.jpg', 'Bureau-8-Visible-PrÃ©fÃ©rÃ©e.jpg');

INSERT INTO projet.photos(
	photo_id, pictures, name)
	VALUES (DEFAULT, '/src/main/resources/photos/Coiffeuse_1_Details.jpg', 'Coiffeuse_1_Details.jpg');

INSERT INTO projet.photos(
	photo_id, pictures, name)
	VALUES (DEFAULT, '/src/main/resources/photos/Coiffeuse_1-Visible_PrÃ©fÃ©rÃ©e.jpg', 'Coiffeuse_1-Visible_PrÃ©fÃ©rÃ©e.jpg');

INSERT INTO projet.photos(
	photo_id, pictures, name)
	VALUES (DEFAULT, '/src/main/resources/photos/Coiffeuse_2.jpg', 'Coiffeuse_2.jpg');

INSERT INTO projet.photos(
	photo_id, pictures, name)
	VALUES (DEFAULT, '/src/main/resources/photos/Coiffeuse_2-Visible_PrÃ©fÃ©rÃ©e.jpg', 'Coiffeuse_2-Visible_PrÃ©fÃ©rÃ©e.jpg');



------------------------
---INSERT PHOTO-VISIT---
------------------------
------------------------

INSERT INTO projet.photos_visits(
	visit, photo)
	VALUES (1, 1);

INSERT INTO projet.photos_visits(
	visit, photo)
	VALUES (1, 2);

INSERT INTO projet.photos_visits(
	visit, photo)
	VALUES (1, 3);

INSERT INTO projet.photos_visits(
	visit, photo)
	VALUES (2, 4);

INSERT INTO projet.photos_visits(
	visit, photo)
	VALUES (3, 5);

INSERT INTO projet.photos_visits(
	visit, photo)
	VALUES (3, 6);

INSERT INTO projet.photos_visits(
	visit, photo)
	VALUES (4, 7);

INSERT INTO projet.photos_visits(
	visit, photo)
	VALUES (5, 8);

INSERT INTO projet.photos_visits(
	visit, photo)
	VALUES (5, 9);

INSERT INTO projet.photos_visits(
	visit, photo)
	VALUES (5, 10);

INSERT INTO projet.photos_visits(
	visit, photo)
	VALUES (5, 11);

INSERT INTO projet.photos_visits(
	visit, photo)
	VALUES (5, 12);

INSERT INTO projet.photos_visits(
	visit, photo)
	VALUES (5, 13);

INSERT INTO projet.photos_visits(
	visit, photo)
	VALUES (5, 14);

INSERT INTO projet.photos_visits(
	visit, photo)
	VALUES (5, 15);

INSERT INTO projet.photos_visits(
	visit, photo)
	VALUES (5, 16);

INSERT INTO projet.photos_visits(
	visit, photo)
	VALUES (5, 17);


------------------
---INSERT TYPES---
------------------
------------------

INSERT INTO projet.types(
	type_id, name)
	VALUES (DEFAULT, 'Armoire');
INSERT INTO projet.types(
	type_id, name)
	VALUES (DEFAULT, 'Bahut');
INSERT INTO projet.types(
	type_id, name)
	VALUES (DEFAULT, 'Bibliothèque');
INSERT INTO projet.types(
	type_id, name)
	VALUES (DEFAULT, 'Bonnetière');
INSERT INTO projet.types(
	type_id, name)
	VALUES (DEFAULT, 'Buffet');
INSERT INTO projet.types(
	type_id, name)
	VALUES (DEFAULT, 'Bureau');
INSERT INTO projet.types(
	type_id, name)
	VALUES (DEFAULT, 'Chaise');
INSERT INTO projet.types(
	type_id, name)
	VALUES (DEFAULT, 'Chiffonnier');
INSERT INTO projet.types(
	type_id, name)
	VALUES (DEFAULT, 'Coffre');
INSERT INTO projet.types(
	type_id, name)
	VALUES (DEFAULT, 'Coiffeuse');
INSERT INTO projet.types(
	type_id, name)
	VALUES (DEFAULT, 'Commode');
INSERT INTO projet.types(
	type_id, name)
	VALUES (DEFAULT, 'Confident / Indiscret');
INSERT INTO projet.types(
	type_id, name)
	VALUES (DEFAULT, 'Console');
INSERT INTO projet.types(
	type_id, name)
	VALUES (DEFAULT, 'Dresse');
INSERT INTO projet.types(
	type_id, name)
	VALUES (DEFAULT, 'Fauteuil');
INSERT INTO projet.types(
	type_id, name)
	VALUES (DEFAULT, 'Guéridon');
INSERT INTO projet.types(
	type_id, name)
	VALUES (DEFAULT, 'Lingère');
INSERT INTO projet.types(
	type_id, name)
	VALUES (DEFAULT, 'Lit');
INSERT INTO projet.types(
	type_id, name)
	VALUES (DEFAULT, 'Penderie');
INSERT INTO projet.types(
	type_id, name)
	VALUES (DEFAULT, 'Secrétaire');
INSERT INTO projet.types(
	type_id, name)
	VALUES (DEFAULT, 'Table');
INSERT INTO projet.types(
	type_id, name)
	VALUES (DEFAULT, 'Tabouret');
INSERT INTO projet.types(
	type_id, name)
	VALUES (DEFAULT, 'Vaisselier');
INSERT INTO projet.types(
	type_id, name)
	VALUES (DEFAULT, 'Valet muet');


----------------------
---INSERT FURNITURE---
----------------------
----------------------


INSERT INTO projet.furnitures(
	furniture_id, furniture_title, purchase_price, furniture_date_collection, selling_price, special_sale_price, state_furniture, deposit_date, date_of_sale, sale_withdrawal_date, delivery, type, buyer, seller, pick_up_date)
	VALUES (DEFAULT, 'Bahut profond', 200, NULL, 0, 0, 'M', '2021-03-30 00:00:01', NULL, NULL, NULL, 2, NULL, 4, '2021-03-30 00:00:01');

INSERT INTO projet.furnitures(
	furniture_id, furniture_title, purchase_price, furniture_date_collection, selling_price, special_sale_price, state_furniture, deposit_date, date_of_sale, sale_withdrawal_date, delivery, type, buyer, seller, pick_up_date)
	VALUES (DEFAULT, 'Large Bureau', 159, NULL, 299, 0, 'EV', '2021-03-30 00:00:01', NULL, NULL, NULL, 6, NULL, 4, '2021-03-30 00:00:01');

INSERT INTO projet.furnitures(
	furniture_id, furniture_title, purchase_price, furniture_date_collection, selling_price, special_sale_price, state_furniture, deposit_date, date_of_sale, sale_withdrawal_date, delivery, type, buyer, seller, pick_up_date)
	VALUES (DEFAULT, 'Table en chêne', 140, NULL, 459, 0, 'RE', '2021-03-29 00:00:01', NULL, NULL, NULL, 21, NULL, 5, '2021-03-30 00:00:01');

INSERT INTO projet.furnitures(
	furniture_id, furniture_title, purchase_price, furniture_date_collection, selling_price, special_sale_price, state_furniture, deposit_date, date_of_sale, sale_withdrawal_date, delivery, type, buyer, seller, pick_up_date)
	VALUES (DEFAULT, 'Sécretaire en acajou marqueterie', 90, NULL, 0, 0, 'M', '2021-03-29 00:00:01', NULL, NULL, NULL, 20, NULL, 5, '2021-03-30 00:00:01');

INSERT INTO projet.furnitures(
	furniture_id, furniture_title, purchase_price, furniture_date_collection, selling_price, special_sale_price, state_furniture, deposit_date, date_of_sale, sale_withdrawal_date, delivery, type, buyer, seller, pick_up_date)
	VALUES (DEFAULT, 'Bureau en bois ciré', 220, NULL, 0, 0, 'ER', '2021-03-27 00:00:01', NULL, NULL, NULL, 6, NULL, 3, '2021-03-27 00:00:01');

INSERT INTO projet.furnitures(
	furniture_id, furniture_title, purchase_price, furniture_date_collection, selling_price, special_sale_price, state_furniture, deposit_date, date_of_sale, sale_withdrawal_date, delivery, type, buyer, seller, pick_up_date)
	VALUES (DEFAULT, 'Bureau en chêne massif', 325, NULL, 378, 0, 'EV', '2021-03-27 00:00:01', NULL, NULL, NULL, 6, NULL, 3, '2021-03-27 00:00:01');

INSERT INTO projet.furnitures(
	furniture_id, furniture_title, purchase_price, furniture_date_collection, selling_price, special_sale_price, state_furniture, deposit_date, date_of_sale, sale_withdrawal_date, delivery, type, buyer, seller, pick_up_date)
	VALUES (DEFAULT, 'Magnifique bureau en acajou', 180, NULL, 239, 0, 'EV', '2021-03-27 00:00:01', NULL, NULL, NULL, 6, NULL, 3, '2021-03-27 00:00:01');

INSERT INTO projet.furnitures(
	furniture_id, furniture_title, purchase_price, furniture_date_collection, selling_price, special_sale_price, state_furniture, deposit_date, date_of_sale, sale_withdrawal_date, delivery, type, buyer, seller, pick_up_date)
	VALUES (DEFAULT, 'Splendide coiffeuse aux reliefs travaillés', 150, NULL, 199, 0, 'EV', '2021-03-27 00:00:01', NULL, NULL, NULL, 10, NULL, 3, '2021-03-27 00:00:01');

INSERT INTO projet.furnitures(
	furniture_id, furniture_title, purchase_price, furniture_date_collection, selling_price, special_sale_price, state_furniture, deposit_date, date_of_sale, sale_withdrawal_date, delivery, type, buyer, seller, pick_up_date)
	VALUES (DEFAULT, 'Coiffeuse marqueterie ', 145, NULL, 199, 0, 'EV', '2021-03-27 00:00:01', NULL, NULL, NULL, 10, NULL, 3, '2021-03-27 00:00:01');


----------------------------
---INSERT PHOTO-FURNITURE---
----------------------------
----------------------------

INSERT INTO projet.photos_furniture(
	is_visible, is_favourite_photo, photo_id, furniture)
	VALUES (FALSE, FALSE, 1, 1);

INSERT INTO projet.photos_furniture(
	is_visible, is_favourite_photo, photo_id, furniture)
	VALUES (FALSE, FALSE, 2, 2);

INSERT INTO projet.photos_furniture(
	is_visible, is_favourite_photo, photo_id, furniture)
	VALUES (TRUE, TRUE, 3, 2);

INSERT INTO projet.photos_furniture(
	is_visible, is_favourite_photo, photo_id, furniture)
	VALUES (FALSE, FALSE, 5, 3);

INSERT INTO projet.photos_furniture(
	is_visible, is_favourite_photo, photo_id, furniture)
	VALUES (FALSE, FALSE, 6, 4);

INSERT INTO projet.photos_furniture(
	is_visible, is_favourite_photo, photo_id, furniture)
	VALUES (FALSE, FALSE, 8, 5);

INSERT INTO projet.photos_furniture(
	is_visible, is_favourite_photo, photo_id, furniture)
	VALUES (FALSE, FALSE, 9, 6);

INSERT INTO projet.photos_furniture(
	is_visible, is_favourite_photo, photo_id, furniture)
	VALUES (TRUE, FALSE, 10, 6);

INSERT INTO projet.photos_furniture(
	is_visible, is_favourite_photo, photo_id, furniture)
	VALUES (TRUE, TRUE, 11, 6);

INSERT INTO projet.photos_furniture(
	is_visible, is_favourite_photo, photo_id, furniture)
	VALUES (FALSE, FALSE, 12, 7);

INSERT INTO projet.photos_furniture(
	is_visible, is_favourite_photo, photo_id, furniture)
	VALUES (TRUE, TRUE, 13, 7);

INSERT INTO projet.photos_furniture(
	is_visible, is_favourite_photo, photo_id, furniture)
	VALUES (FALSE, FALSE, 14, 8);

INSERT INTO projet.photos_furniture(
	is_visible, is_favourite_photo, photo_id, furniture)
	VALUES (TRUE, TRUE, 15, 8);

INSERT INTO projet.photos_furniture(
	is_visible, is_favourite_photo, photo_id, furniture)
	VALUES (FALSE, FALSE, 16, 9);

INSERT INTO projet.photos_furniture(
	is_visible, is_favourite_photo, photo_id, furniture)
	VALUES (TRUE, TRUE, 17, 9);