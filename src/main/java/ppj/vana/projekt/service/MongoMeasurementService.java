package ppj.vana.projekt.service;

import com.mongodb.DBObject;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ppj.vana.projekt.model.City;
import ppj.vana.projekt.model.Measurement;
import ppj.vana.projekt.model.repository.MeasurementRepository;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.springframework.data.mongodb.core.query.Criteria.where;

/**
 * The type Mongo measurement service.
 */
public class MongoMeasurementService implements IService<Measurement, ObjectId> {

    private static final Logger logger = LoggerFactory.getLogger(MongoMeasurementService.class);

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


    // ---------------------------------------------------------------- CUSTOM PUBLIC METHODS

    public List<Measurement> findAllRecordForCities(List<Integer> citiesID) {
        return mongo.find(Query.query(where("cityID").in(citiesID)), Measurement.class);
    }

    public List<DBObject> readAverage(Integer cityID) {
        if (cityID == null) {
            logger.error("Method readAverage(Integer cityID) was called with no cityID filled.");
            throw new NullPointerException("Method readAverage(Integer cityID) was called with no cityID filled.");
        }

        // prepeare mongo aggregation
        MatchOperation matchStage = Aggregation.match(new Criteria("_id").is(cityID)); // to podle čeho se grupuje se nastavuje jako nové ID... hledám ne cityID, ale _id
        ProjectionOperation projection = Aggregation.project("temperature", "humidity", "pressure", "wind", "cityID");
        GroupOperation group = Aggregation.group("cityID")
                .avg("temperature").as("avgTemperature")
                .avg("humidity").as("avgHumidity")
                .avg("pressure").as("avgPressure")
                .avg("wind").as("avgWind");

        TypedAggregation<Measurement> aggregation = Aggregation.newAggregation(Measurement.class, projection, group, matchStage);
        return mongo.aggregate(aggregation, DBObject.class).getMappedResults();
    }

    public List<DBObject> readAverageAllCities() {
        TypedAggregation<Measurement> aggregation = Aggregation.newAggregation(Measurement.class,
                Aggregation.project("temperature", "humidity", "pressure", "wind", "cityID"),
                Aggregation.group("cityID")
                        .avg("temperature").as("avgTemperature")
                        .avg("humidity").as("avgHumidity")
                        .avg("pressure").as("avgPressure")
                        .avg("wind").as("avgWind")
        );
        AggregationResults<DBObject> result = mongo.aggregate(aggregation, DBObject.class);
        List<DBObject> resultList = result.getMappedResults();
        return resultList;
    }

    public List<Measurement> findAllRecordForCityID(Integer cityID) {
        return mongo.find(Query.query(where("cityID").is(cityID)), Measurement.class);
    }

    public Measurement getByHexaString(String id) {
        ObjectId objectId = new ObjectId(id);
        return mongo.findOne(Query.query(where("_id").is(objectId)), Measurement.class);
    }

    public Boolean existsByHexaString(String id) {
        ObjectId objectId = new ObjectId(id);
        return measurementRepository.existsById(objectId);
    }

    public Measurement update(Measurement measurement) {
        measurementRepository.save(measurement);
        return measurement;
    }

    /**
     * cityID - city
     * timestampSeconds - select only data measured after this timepstamp (newer ones)
     */
    public List<Measurement> getMeasurementsForCityAfterTimestamp(Integer cityID, Long timestampSeconds) {
        return mongo.find(Query.query(where("cityID").is(cityID).and("timeOfMeasurement").gt(timestampSeconds)), Measurement.class);
    }

    /**
     * timestampSeconds - select only data measured before this timepstamp (newer ones)
     */
    public List<Measurement> getMeasurementBeforeTimestamp(Long timestampSeconds) {
        return mongo.find(Query.query(where("timeOfMeasurement").lt(timestampSeconds)), Measurement.class);
    }

    /**
     * Vrátí instanci Measurement uvnitř které jsou zprůměrované hodnoty.
     *
     * @param cityName the city name
     * @param days     udává kolik dnů "dozadu" bude výpočet zahrnovat. Range 1-365.
     * @return the string
     */
    public String averageValuesForCity(String cityName, int days) {
        // city does not exists? error
        if (!cityService.existsById(cityName))
            return "City " + cityName + " does not exist.";

        City city = cityService.get(cityName);
        // city does not have connection with mongoDB? error
        Integer cityID = city.getOpenWeatherMapID();
        if (cityID == null)
            return "City " + cityName + " does not have any measured model.";

        // rozsah dnů je 1-365, jinak null
        if (days < 1 || days > 365)
            return "You can calculate average back to 1-365 days.";

        // timestamp x days back
        Long timestampSeconds = UtilService.getTimestampXDaysBackInSeconds(days);
        // filtered measurements
        List<Measurement> filteredList = getMeasurementsForCityAfterTimestamp(cityID, timestampSeconds);

        if (filteredList.isEmpty())
            return "No measured data in requested interval.";

        String output = String.format("Averaged values for %s in %d last days:", cityName, days) + System.lineSeparator();
        output += calculateAndSummarizeAverage(filteredList);
        logger.info(output);
        return output;
    }


    // ------------------------------------------------ INTERFACE @Override
    @Override
    public List<Measurement> getAll() {
        return measurementRepository.findAll();
    }

    @Override
    public boolean exists(Measurement measurement) {
        return measurementRepository.existsById(measurement.getId());
    }

    @Override
    public Measurement get(ObjectId objectId) {
        return mongo.findOne(Query.query(where("_id").is(objectId)), Measurement.class);
    }

    @Override
    public long count() {
        return measurementRepository.count();
    }

    @Override
    public void deleteAll() {
        measurementRepository.deleteAll();
    }

    @Override
    public void add(Measurement entity) {
        mongo.insert(entity);
    }

    @Override
    public void delete(Measurement measurement) {
        mongo.remove(measurement);
    }

    // ------------------------------------------------ PRIVATE METHODS

    private String calculateAndSummarizeAverage(List<Measurement> filteredList) {
        double averageTemperature = 0;
        double averageHumidity = 0;
        double averagePressure = 0;
        double averageWind = 0.0;

        for (Measurement m : filteredList) {
            // pokud není vyplněno u záznamu vše, nebudu ho do průměru počítat
            if (m.getTemperature() == null || m.getHumidity() == null || m.getPressure() == null || m.getWind() == null)
                continue;
            averageTemperature += m.getTemperature();
            averageHumidity += m.getHumidity();
            averagePressure += m.getPressure();
            averageWind += m.getWind();
        }
        int numberOfRecords = filteredList.size();
        String output = String.format("Temperature: %.1f", averageTemperature / numberOfRecords) + System.lineSeparator();
        output += String.format("Humidity: %.1f", averageHumidity / numberOfRecords) + System.lineSeparator();
        output += String.format("Pressure: %.1f", averagePressure / numberOfRecords) + System.lineSeparator();
        output += String.format("Wind speed: %.1f", averageWind / numberOfRecords) + System.lineSeparator();
        return output;
    }


    // ------------------------------------------------ MAP-REDUCE

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
        // id == cityID -- to si definujeme v map
        public int id; // TOTO SE MUSI SHODOVAT S ID DANE ENTITY (Measurement), jinak se to POSE*E
        public int value; // count
    }

}
