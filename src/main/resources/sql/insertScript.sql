-- INSERT SCRIPT PRO PPJ

-- country
INSERT INTO Country (name) VALUES ("Germany");
INSERT INTO Country (name) VALUES ("Slovakia");
INSERT INTO Country (name) VALUES ("Czech Republic");
INSERT INTO Country (name) VALUES ("Spain");
INSERT INTO Country (name) VALUES ("Finland");


-- City
INSERT INTO City (name,country,openWeatherMapID) VALUES ("Prague","Czech Republic",3067696);
INSERT INTO City (name,country,openWeatherMapID) VALUES ("Brno","Czech Republic",3078610);
INSERT INTO City (name,country,openWeatherMapID) VALUES ("Pilsen","Czech Republic",3068160);
INSERT INTO City (name,country,openWeatherMapID) VALUES ("Ostrava","Czech Republic",3068799);
INSERT INTO City (name,country,openWeatherMapID) VALUES ("Liberec","Czech Republic",3071961);

INSERT INTO City (name,country,openWeatherMapID) VALUES ("Berlin","Germany",2950159);
INSERT INTO City (name,country,openWeatherMapID) VALUES ("Frankfurt","Germany",3220802);
INSERT INTO City (name,country,openWeatherMapID) VALUES ("Stuttgart","Germany",2825297);

commit;
