﻿# TODO LIST

##Iterace 2 - X.X.2019 (PO)
- [ ] Zamyslet se, zda by neslo lepe implementovat RESTControllery - s dedicnosti a generickymi typy  <br />
- [ ] Vygenerovat dokumentaci
- [ ] Print REST API, MVC API usage
- [ ] Vyměnit u entity Measurement uložení TTL při volání service metody pro add(), raději využít anotaci @PrePersist - zamezí se tak prodloužení TTL updatem.

- [x] No debug print to CMD
- [x] All comments in English
- [x] Relative path - Opravit tlačítko submit u select country .. rozbiji se protoze neni relativni a na deploy je jina adresa http://localhost:8080/meteorolog/measurementByCountry <br />
- [x] Measurements v MVC do tabulky
- [x] Přidat do DB tabulku pro ukládání datumu měření. Podle toho se bude checkovat case, kdy se provedl restart, aby nebyl download dat pres API veicekrat za 10 minut
- [x] MongoDB TTL
- [x] Dodělat testy na service classes - genericky test dle IService
- [x] udělat @Transactional pristup v service
- [x] Počítat average přímo dotazem na MongoDB, namísto v Javě - rozpracovan - začít používat MongoMeasurementService.readAverage()<br />
- [x] Zkrášlit response na average REST API requesty - aby to v prohlížeči vypadalo lidstky a ne číslo s 10.0000000001 <br />
- [x] u HTML APi udělat tlačítko zpět na hlavní stránku (přesměrování na localhost/ )
- [x] Maven Deploy - samostně spustitelná web app - deploy pomocí .WAR na TomCat 9.0.19 <br /> 
- [x] Konfigurovatelná expirace záznamů měření (default 14 dnů) <br />
- [x] Automatická aktualizace dat a její nastavení (uvnitř .properties) <br />
- [x] Read-only mód <br />
- [x] implementace obecného interface v service třídách
- [x] upravení modelu (smazat sunrise-sunset)   <br />
- [x] Stáhnout nějaký plugin pro javadoc <br />
- [x] MVC API dodělat selekci dle státu.<br />
- [x] MongoDB - Měření  (včetně testů s embedd Mongo)<br />
- [x] Komunikace a čerpání dat z OWM.com API<br />
- [x] REST API + testy<br />

##Iterace 1 - 15.4.2019 (PO)
Datovy model - MySQL - relace {Stat,Mesto} ✓<br />
Perzistence ✓<br />
Testovani perzistence ✓<br />
Externi konfigurace - properties soubory ✓<br />
Logování - lognout do souboru cas provedeni Main.java ✓<br />
Sestaveni pomoci Maven ✓<br /> 
