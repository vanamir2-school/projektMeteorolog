package ppj.vana.projekt.dao;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ppj.vana.projekt.model.Measurement;

import java.util.List;

@Repository
public interface MeasurementRepository extends MongoRepository<Measurement, ObjectId> {

    List<Measurement> findByCityID(Integer cityID, Pageable pageable);

}
