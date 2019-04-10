package ppj.vana.projekt.service;

import org.bson.types.ObjectId;
import ppj.vana.projekt.data.Measurement;

public interface MeasurementService {

    Measurement find(Integer objectId);

    Measurement add(Measurement user);

    void remove(Measurement user);
}
