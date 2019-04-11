package ppj.vana.projekt.repositories;


import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ppj.vana.projekt.data.Measurement;

import java.util.List;
import java.util.stream.Stream;

@Repository
public interface MeasurementRepository extends MongoRepository<Measurement, ObjectId> {

    List<Measurement> findByCityID(Integer cityID, Pageable pageable);

    // TODO - error nejspis protoze chybi map and reduce
    //  Stream<Measurement> streamAll();


}
