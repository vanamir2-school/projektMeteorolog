# TODO LIST

##Iterace 2 - X.X.2019 (PO)
- [ ] u HTML APi udělat tlačítko zpět na hlavní stránku (přesměrování na localhost/ )
- [ ] implementace obecného interface v service třídách
- [ ] upravení modelu (smazat sunrise-sunset) + nastavit u entit držení si více atributů (country si bude pamatovat všechna města - List<City> pomoc9 anotace... lazy load)
- [ ] udělat @Transactional pristup v service
- [ ] Konfigurovatelná expirace záznamů měření (default 14 dnů) <br />
- [ ] Read-only mód  <br />
- [ ] Automatická aktualizace dat a její nastavení  <br />
- [ ] Maven Deploy - samostně spustitelná web app  <br />
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
