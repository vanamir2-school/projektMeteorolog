-- CREATE SCHEMA `ppj` ;

drop table IF EXISTS City;
drop table IF EXISTS Country;

Create table Country(
    name VARCHAR(80) PRIMARY KEY NOT NULL
);

Create table City(
                     name    VARCHAR(80) PRIMARY KEY NOT NULL,
                     country VARCHAR(80)             NOT NULL,
                     FOREIGN KEY (country)
                         REFERENCES Country (name)
                         ON DELETE CASCADE
);
commit;
