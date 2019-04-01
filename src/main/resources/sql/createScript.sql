-- CREATE SCHEMA `ppj` ;

drop table IF EXISTS Stat;
drop table IF EXISTS Mest;

Create table Stat (
	nazev Varchar(80) NOT NULL,
 Primary Key (nazev));

Create table Mesto (
	nazev Varchar(80) NOT NULL,
	stat Varchar(80) NOT NULL,
 Primary Key (nazev));

Alter table Mesto add Foreign Key (stat) references Stat (nazev) on delete  restrict on update  restrict;
commit;
