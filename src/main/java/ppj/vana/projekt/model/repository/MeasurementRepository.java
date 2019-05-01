package ppj.vana.projekt.model.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ppj.vana.projekt.model.Measurement;

@Repository
public interface MeasurementRepository extends MongoRepository<Measurement, ObjectId> {

}
