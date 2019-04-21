# TIPs

### .MD tips
https://github.com/tchapi/markdown-cheatsheet/blob/master/README.md

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
 ##### package to single jar with dependecies
 mvn package:single

### Path to project folder 
 - C:\Users\mirav\IdeaProjects\projektMeteorolog

### openWeatherMap - ID+KEZ+metrickéJednotky
http://api.openweathermap.org/data/2.5/weather?id=3077929&APPID=8511369eded1edc6193a34676e82dec5&units=metric


### MongoDB - testovani v CMD
D:\Program Files\MongoDB\bin> .\mongo.exe<br />
use meteorolog<br />
var mapFunction = function map(){     return emit(this.cityID,1); };<br />
var reduceFunction = function reduce(key, values) {     return Array.sum( values ); };<br />
db.meteorolog.mapReduce(mapFunction,reduceFunction,{ out: "map_reduce_example" } ).find()<br />

### Vyhledani PID dle portu (+ zabiti)- powershell:
netstat -a -o | Select-String "8080"<br />
Taskkill /PID 12276 /F

### REST API
 Spring guide - https://spring.io/guides/gs/rest-service/ <br />
Chrome REST client - https://github.com/jarrodek/ChromeRestClient

### JSON + ObjectMapper (de/serializace)
https://www.baeldung.com/jackson-object-mapper-tutorial

### JSP and JSTL
JSP technology is used to create web application just like Servlet technology. It can be thought of as an extension to Servlet because it provides more functionality than servlet such as expression language, JSTL, etc. > https://www.javatpoint.com/jsp-tutorial<br />

The JSP Standard Tag Library (JSTL) represents a set of tags to simplify the JSP development > 
https://www.javatpoint.com/jstl

Tvorba komboBoxu > https://dzone.com/tutorials/java/struts/struts-example/struts-html-select-tag-example-1.html

### Javadoc plugin
https://github.com/setial/intellij-javadocs/wiki<br />
To generate javadocs for active element press "shift + alt + G".<br />
To generate javadocs for all elements in current java file press "shift + ctrl + alt + G".<br />

### Project structure
https://softwareengineering.stackexchange.com/questions/175950/in-mvc-dao-should-be-called-from-controller-or-model

#### 3-tier architecture:<br />
 * data: persisted data;<br />
 * service: logical part of the application;<br />
 * presentation: hmi, webservice...<br />

#####  The MVC pattern takes place in the presentation tier of the above architecture (for a webapp):<br />

* data<br />
* service<br />
* presentation:<br />
    * controller: intercepts the HTTP request and returns the HTTP response<br />
    * model: stores data to be displayed/treated<br />
    * view: organises output/display.<br />
    
##### Another view> https://www.tutorialspoint.com/spring_boot/spring_boot_code_structure.htm

### TomCat deploy
CMD: <TomCat_Home>\bin> .\catalina.bat run <br />
Browser: http://localhost:8080/meteorolog/

