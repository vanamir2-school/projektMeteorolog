package ppj.vana.projekt.service;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import ppj.vana.projekt.data.Measurement;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

public class MongoMeasurementService implements MeasurementService {

    private final MongoOperations mongo;

    public MongoMeasurementService(MongoOperations mongo) {
        this.mongo = mongo;
    }

    public List<Measurement> findAllRecordForCityID(Integer cityID) {
        return mongo.find(Query.query(where("cityID").is(cityID)), Measurement.class);
    }

    @Override
    public Measurement find(ObjectId objectId) {
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
}
