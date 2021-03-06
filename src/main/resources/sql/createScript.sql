-- CREATE SCHEMA `ppj` ;

DROP TABLE IF EXISTS City;
DROP TABLE IF EXISTS Country;

CREATE TABLE Country
(
    name VARCHAR(80) PRIMARY KEY NOT NULL
);

CREATE TABLE MesHistory
(
    time TIMESTAMP PRIMARY KEY NOT NULL
);

CREATE TABLE City
(
    name             VARCHAR(80) PRIMARY KEY NOT NULL,
    country          VARCHAR(80)             NOT NULL,
    openWeatherMapID INT                     NULL,
    FOREIGN KEY (country)
        REFERENCES Country (name)
        ON DELETE CASCADE
);

COMMIT;
