-- INSERT SCRIPT PRO PPJ

-- country
INSERT INTO Country (name) VALUES ("Belgie");
INSERT INTO Country (name) VALUES ("Bulharsko");
INSERT INTO Country (name) VALUES ("Česká republika");
INSERT INTO Country (name) VALUES ("Dánsko");
INSERT INTO Country (name) VALUES ("Estonsko");
INSERT INTO Country (name) VALUES ("Finsko");
INSERT INTO Country (name) VALUES ("Francie");
INSERT INTO Country (name) VALUES ("Chorvatsko");
INSERT INTO Country (name) VALUES ("Irsko");
INSERT INTO Country (name) VALUES ("Itálie");
INSERT INTO Country (name) VALUES ("Kypr");
INSERT INTO Country (name) VALUES ("Litva");
INSERT INTO Country (name) VALUES ("Lotyšsko");
INSERT INTO Country (name) VALUES ("Lucembursko");
INSERT INTO Country (name) VALUES ("Maďarsko");
INSERT INTO Country (name) VALUES ("Malta");
INSERT INTO Country (name) VALUES ("Německo");
INSERT INTO Country (name) VALUES ("Nizozemsko");

-- City
INSERT INTO City (name,country) VALUES ("Praha","Česká republika");
INSERT INTO City (name,country) VALUES ("Brno","Česká republika");
INSERT INTO City (name,country) VALUES ("Ostrava","Česká republika");
INSERT INTO City (name,country) VALUES ("Plzeň","Česká republika");
INSERT INTO City (name,country) VALUES ("Olomouc","Česká republika");
INSERT INTO City (name,country) VALUES ("Liberec","Česká republika");
INSERT INTO City (name,country) VALUES ("Ústí nad Labem","Česká republika");
INSERT INTO City (name,country) VALUES ("České Budějovice","Česká republika");
INSERT INTO City (name,country) VALUES ("Hradec Králové","Česká republika");
INSERT INTO City (name,country) VALUES ("Pardubice","Česká republika");
INSERT INTO City (name,country) VALUES ("Havířov","Česká republika");
INSERT INTO City (name,country) VALUES ("Zlín","Česká republika");
INSERT INTO City (name,country) VALUES ("Kladno","Česká republika");
INSERT INTO City (name,country) VALUES ("Most","Česká republika");
INSERT INTO City (name,country) VALUES ("Karviná","Česká republika");
INSERT INTO City (name,country) VALUES ("Opava","Česká republika");
INSERT INTO City (name,country) VALUES ("Frýdek-Místek","Česká republika");
INSERT INTO City (name,country) VALUES ("Děčín","Česká republika");
INSERT INTO City (name,country) VALUES ("Teplice","Česká republika");
INSERT INTO City (name,country) VALUES ("Karlovy Vary","Česká republika");
INSERT INTO City (name,country) VALUES ("Jihlava","Česká republika");
INSERT INTO City (name,country) VALUES ("Chomutov","Česká republika");
INSERT INTO City (name,country) VALUES ("Přerov","Česká republika");
INSERT INTO City (name,country) VALUES ("Prostějov","Česká republika");
INSERT INTO City (name,country) VALUES ("Jablonec nad Nisou","Česká republika");
INSERT INTO City (name,country) VALUES ("Mladá Boleslav","Česká republika");
INSERT INTO City (name,country) VALUES ("Třebíč","Česká republika");
INSERT INTO City (name,country) VALUES ("Berlín","Německo");
INSERT INTO City (name, country, openWeatherMapID)
VALUES ("Česká Lípa", "Česká republika", 3077929);

commit;
