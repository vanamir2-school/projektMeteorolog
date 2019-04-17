package ppj.vana.projekt.service;

import org.bson.types.ObjectId;
import ppj.vana.projekt.model.Measurement;

// TODO UDELAT INTEFRACE PRO VSECHNY CONTROLLERY
public interface MeasurementService {

    Measurement getByID(ObjectId objectId);

    Measurement add(Measurement user);

    void remove(Measurement user);
}
