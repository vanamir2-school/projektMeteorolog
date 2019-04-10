package ppj.vana.projekt.service;

import org.springframework.data.mongodb.core.MongoOperations;
import ppj.vana.projekt.data.Measurement;

public class MongoMeasurementService implements MeasurementService {

    private final MongoOperations mongo;

    public MongoMeasurementService(MongoOperations mongo) {
        this.mongo = mongo;
    }

    @Override
    public Measurement find(Integer objectId) {
        return null;
    }

    @Override
    public Measurement add(Measurement user) {
        return null;
    }

    @Override
    public void remove(Measurement user) {

    }
}
