-- CREATE SCHEMA `ppj` ;

drop table IF EXISTS City;
drop table IF EXISTS Country;

Create table Country (
	name Varchar(80) NOT NULL,
 Primary Key (name));

Create table City (
	name Varchar(80) NOT NULL,
	country Varchar(80) NOT NULL,
 Primary Key (name));

Alter table City add Foreign Key (country) references Country (name) on delete  restrict on update  restrict;
commit;
