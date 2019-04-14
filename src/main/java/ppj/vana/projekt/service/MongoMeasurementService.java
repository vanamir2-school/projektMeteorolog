package ppj.vana.projekt.service;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.data.mongodb.core.query.Query;
import ppj.vana.projekt.Main;
import ppj.vana.projekt.data.City;
import ppj.vana.projekt.data.Measurement;
import ppj.vana.projekt.repositories.MeasurementRepository;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.springframework.data.mongodb.core.query.Criteria.where;

public class MongoMeasurementService implements MeasurementService {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static final long ONE_DAY_MILISSECONDS = 86400000;
    private final MongoOperations mongo;
    @Autowired
    WeatherDownloaderService weatherDownloaderService;
    @Autowired
    private MeasurementRepository measurementRepository;
    @Autowired
    private CityService cityService;


    public MongoMeasurementService(MongoOperations mongo) {
        this.mongo = mongo;
    }

    /**
     * Vrátí instanci Measurement uvnitř které jsou zprůměrované hodnoty.
     *
     * @param days udává kolik dnů "dozadu" bude výpočet zahrnovat. Range 1-365.
     */
    public String averageValuesForCity(String cityName, int days) {
        // city does not exists? null
        if (!cityService.exists(cityName))
            return "City " + cityName + "does not exist.";
        City city = cityService.getByName(cityName).get();
        // city does not have connection with mongoDB? null
        Integer cityID = city.getOpenWeatherMapID();
        if (cityID == null)
            return "City " + cityName + "does not have any measured data.";

        // rozsah dnů je 1-365, jinak null
        if (days < 1 || days > 365)
            return "You can calculate average back to 1-365 days.";
        Date currentTime = new Date();

        Long timestamp = currentTime.getTime() - ONE_DAY_MILISSECONDS * days;
        logger.info("Aktuální čas: " + weatherDownloaderService.timestampToStringMilliSeconds(currentTime.getTime()));
        logger.info("Průměr se počítá od: " + weatherDownloaderService.timestampToStringMilliSeconds(timestamp));

       /* System.out.println(timestamp);
        System.out.println(weatherDownloaderService.timestampToStringMilliSeconds(currentTime.getTime()));
        System.out.println(weatherDownloaderService.timestampToStringMilliSeconds(timestamp));*/

        double temperature = 0;
        double humidity = 0;
        double pressure = 0;
        double wind = 0.0;
        List<Measurement> filteredList = mongo.find(Query.query(where("cityID").is(cityID).and("timeOfMeasurement").gt(timestamp)), Measurement.class);
        if( filteredList.isEmpty() )
            return "No measured data in requested interval.";

        for (Measurement m : filteredList) {
            // pokud není vyplněno u záznamu vše, nebudu ho do průměru počítat
            if (m.getTemperature() == null || m.getHumidity() == null || m.getPressure() == null || m.getWind() == null)
                continue;
            temperature += m.getTemperature();
            humidity += m.getHumidity();
            pressure += m.getPressure();
            wind += m.getWind();
        }
        int numberOfRecords = filteredList.size();
        String output = String.format("Averaged values for city %s in last %d days:", cityName, days) + System.lineSeparator();
        output += String.format("Temperature: %s", temperature / numberOfRecords) + System.lineSeparator();
        output += String.format("Humidity: %s", humidity / numberOfRecords) + System.lineSeparator();
        output += String.format("Pressure: %s", pressure / numberOfRecords) + System.lineSeparator();
        output += String.format("Wind speed: %s", wind / numberOfRecords) + System.lineSeparator();
        System.out.println(output);
        //filteredList.forEach( (m) -> System.out.println(m.toString()));
        return output;
    }

    public List<Measurement> findAllRecordForCityID(Integer cityID) {
        return mongo.find(Query.query(where("cityID").is(cityID)), Measurement.class);
    }

    public boolean exists(String id) {
        ObjectId objectId = new ObjectId(id);
        return measurementRepository.existsById(objectId);
    }

    public boolean exists(ObjectId objectId) {
        return measurementRepository.existsById(objectId);
    }

    public long count() {
        return measurementRepository.count();
    }

    public void deleteAll() {
        measurementRepository.deleteAll();
    }

    @Override
    public Measurement getByID(ObjectId objectId) {
        return mongo.findOne(Query.query(where("_id").is(objectId)), Measurement.class);
    }

    public Measurement getByID(String id) {
        ObjectId objectId = new ObjectId(id);
        return mongo.findOne(Query.query(where("_id").is(objectId)), Measurement.class);
    }

    public Measurement update(Measurement measurement) {
        measurementRepository.save(measurement);
        return measurement;
    }

    @Override
    public Measurement add(Measurement measurement) {
        mongo.insert(measurement);
        return measurement;
    }

    @Override
    public void remove(Measurement measurement) {
        mongo.remove(measurement);
    }


    //Map-reduce is a data processing paradigm for condensing large volumes of data into useful aggregated results.
    // For map-reduce operations, MongoDB provides the mapReduce database command.
    public Map<Integer, Integer> numOfRecordsUsingMapReduce() {
        final String mapJS = "classpath:mongoDB/measurement_cityID_map.js";
        final String reduceJS = "classpath:mongoDB/measurement_cityID_reduce.js";
        // query , kolekce (coz je seskupeni dokumentu - neco jako RDBMS tabulka), map, reduce, entityClass
        MapReduceResults<CountEntry> mapReduceResult = mongo.mapReduce(new Query(), mongo.getCollectionName(Measurement.class), mapJS, reduceJS, CountEntry.class);
        Map<Integer, Integer> numOfRecords = new TreeMap<>();
        mapReduceResult.forEach((result) -> numOfRecords.put(result.id, result.value));
        return numOfRecords;
    }

    private static class CountEntry {
        // id == cityID -- to si definujeme v map
        public int id; // TOTO SE MUSI SHODOVAT S ID DANE ENTITY (Measurement), jinak se to POSE*E
        public int value; // count
    }

}
