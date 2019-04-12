# TIPs

### IDE
- COMMIT ... CTRL+K<br />
- PUSH  ... CTRL+K+SHIFT<br />
- MAVEN OKNO ... View-Tools-Maven<br />
- Generovani kodu ... ALT+INSERT<br />
- Importy ... CTRL+ALT+O<br />
- Code reformat ... CTRL+ALT+L<br />

### DB CMD connection (MySQL)
- https://stackoverflow.com/questions/44481917/mysql-shell-is-not-able-to-connect-to-mysql-server?rq=1<br />
MySQL Shell: <br />
\sql <br />
\connect root@localhost <br />
USE dbname; <br />

### Maven
 - DEPLOY (cmd):<br />
 mvn deploy<br />
 cd C:\Users\mirav\Desktop\TUL\Semestr 2\PPJ\SEMESTRALKA\projektVana\meteorolog<br />
 java -jar .\meteorolog-0.1_BETA.jar args<br />

#### Path to project folder 
 - C:\Users\mirav\IdeaProjects\projektMeteorolog

#### openWeatherMap - ID+KEZ+metrickéJednotky
http://api.openweathermap.org/data/2.5/weather?id=3077929&APPID=8511369eded1edc6193a34676e82dec5&units=metric


#### MongoDB - testovani v CMD
D:\Program Files\MongoDB\bin> .\mongo.exe<br />
use meteorolog<br />
var mapFunction = function map(){     return emit(this.cityID,1); };<br />
var reduceFunction = function reduce(key, values) {     return Array.sum( values ); };<br />
db.meteorolog.mapReduce(mapFunction,reduceFunction,{ out: "map_reduce_example" } ).find()<br />

#### Vyhledani PID dle portu (+ zabiti)- powershell:
netstat -a -o | Select-String "8080"<br />
Taskkill /PID 12276 /F

#### REST API GUIDE
https://spring.io/guides/gs/rest-service/

