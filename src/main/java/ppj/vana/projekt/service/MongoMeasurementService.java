package ppj.vana.projekt.service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.data.mongodb.core.query.Query;
import ppj.vana.projekt.data.Measurement;
import ppj.vana.projekt.repositories.MeasurementRepository;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.springframework.data.mongodb.core.query.Criteria.where;

public class MongoMeasurementService implements MeasurementService {

    private final MongoOperations mongo;

    @Autowired
    private MeasurementRepository measurementRepository;



    public MongoMeasurementService(MongoOperations mongo) {
        this.mongo = mongo;
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
