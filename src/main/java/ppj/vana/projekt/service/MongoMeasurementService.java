package ppj.vana.projekt.service;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.data.mongodb.core.query.Query;
import ppj.vana.projekt.Main;
import ppj.vana.projekt.model.City;
import ppj.vana.projekt.model.Measurement;
import ppj.vana.projekt.dao.MeasurementRepository;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.springframework.data.mongodb.core.query.Criteria.where;

/**
 * The type Mongo measurement service.
 */
public class MongoMeasurementService implements MeasurementService {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static final long ONE_DAY_MILISSECONDS = 86400000;
    private final MongoOperations mongo;
    /**
     * The Weather downloader service.
     */
    @Autowired
    WeatherDownloaderService weatherDownloaderService;
    @Autowired
    private MeasurementRepository measurementRepository;
    @Autowired
    private CityService cityService;


    /**
     * Instantiates a new Mongo measurement service.
     *
     * @param mongo the mongo
     */
    public MongoMeasurementService(MongoOperations mongo) {
        this.mongo = mongo;
    }

    /**
     * Get all list.
     *
     * @return the list
     */
    public List<Measurement> getAll(){
        return measurementRepository.findAll();
    }

    /**
     * Vrátí instanci Measurement uvnitř které jsou zprůměrované hodnoty.
     *
     * @param cityName the city name
     * @param days     udává kolik dnů "dozadu" bude výpočet zahrnovat. Range 1-365.
     * @return the string
     */
    public String averageValuesForCity(String cityName, int days) {
        // city does not exists? null
        if (!cityService.exists(cityName))
            return "City " + cityName + " does not exist.";
        City city = cityService.getByName(cityName).get();
        // city does not have connection with mongoDB? null
        Integer cityID = city.getOpenWeatherMapID();
        if (cityID == null)
            return "City " + cityName + " does not have any measured model.";

        // rozsah dnů je 1-365, jinak null
        if (days < 1 || days > 365)
            return "You can calculate average back to 1-365 days.";
        Date currentTime = new Date();

        Long timestamp = currentTime.getTime() - ONE_DAY_MILISSECONDS * days;
        Long timestampSeconds = timestamp/1000;
        logger.info("Aktuální čas: " + weatherDownloaderService.timestampToStringMilliSeconds(currentTime.getTime()));
        logger.info("Průměr se počítá od: " + weatherDownloaderService.timestampToStringMilliSeconds(timestamp));

       /* System.out.println(timestamp);
        System.out.println(weatherDownloaderService.timestampToStringMilliSeconds(currentTime.getTime()));
        System.out.println(weatherDownloaderService.timestampToStringMilliSeconds(timestamp));*/

        double temperature = 0;
        double humidity = 0;
        double pressure = 0;
        double wind = 0.0;
        List<Measurement> filteredList = mongo.find(Query.query(where("cityID").is(cityID).and("timeOfMeasurement").gt(timestampSeconds)), Measurement.class);
        if( filteredList.isEmpty() )
            return "No measured model in requested interval.";

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


    /**
     * Find all record for cities list.
     *
     * @param citiesID the cities id
     * @return the list
     */
    public List<Measurement> findAllRecordForCities(List<Integer> citiesID) {
        return mongo.find(Query.query(where("cityID").in(citiesID)), Measurement.class);
    }

    /**
     * Find all record for city id list.
     *
     * @param cityID the city id
     * @return the list
     */
    public List<Measurement> findAllRecordForCityID(Integer cityID) {
        return mongo.find(Query.query(where("cityID").is(cityID)), Measurement.class);
    }

    /**
     * Exists boolean.
     *
     * @param id the id
     * @return the boolean
     */
    public boolean exists(String id) {
        ObjectId objectId = new ObjectId(id);
        return measurementRepository.existsById(objectId);
    }

    /**
     * Exists boolean.
     *
     * @param objectId the object id
     * @return the boolean
     */
    public boolean exists(ObjectId objectId) {
        return measurementRepository.existsById(objectId);
    }

    /**
     * Count long.
     *
     * @return the long
     */
    public long count() {
        return measurementRepository.count();
    }

    /**
     * Delete all.
     */
    public void deleteAll() {
        measurementRepository.deleteAll();
    }

    @Override
    public Measurement getByID(ObjectId objectId) {
        return mongo.findOne(Query.query(where("_id").is(objectId)), Measurement.class);
    }

    /**
     * Gets by id.
     *
     * @param id the id
     * @return the by id
     */
    public Measurement getByID(String id) {
        ObjectId objectId = new ObjectId(id);
        return mongo.findOne(Query.query(where("_id").is(objectId)), Measurement.class);
    }

    /**
     * Update measurement.
     *
     * @param measurement the measurement
     * @return the measurement
     */
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


    /**
     * Num of records using map reduce map.
     *
     * @return the map
     */
//Map-reduce is a model processing paradigm for condensing large volumes of model into useful aggregated results.
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
        /**
         * The Id.
         */
// id == cityID -- to si definujeme v map
        public int id; // TOTO SE MUSI SHODOVAT S ID DANE ENTITY (Measurement), jinak se to POSE*E
        /**
         * The Value.
         */
        public int value; // count
    }

}
