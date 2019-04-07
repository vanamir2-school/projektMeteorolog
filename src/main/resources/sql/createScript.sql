-- CREATE SCHEMA `ppj` ;

drop table IF EXISTS City;
drop table IF EXISTS Country;

Create table Country(
                        name Varchar(80) NOT NULL,
                        Primary Key (name)
);

Create table City(
                     name    Varchar(80) NOT NULL,
                     country Varchar(80) NOT NULL,
                     Primary Key (name),
                     FOREIGN KEY (country)
                         REFERENCES Country (name)
                         ON DELETE CASCADE
);
commit;
