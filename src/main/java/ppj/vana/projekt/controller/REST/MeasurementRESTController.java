package ppj.vana.projekt.controller.REST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ppj.vana.projekt.model.Measurement;
import ppj.vana.projekt.controller.exceptions.APIErrorMessage;
import ppj.vana.projekt.controller.exceptions.APIException;
import ppj.vana.projekt.service.MongoMeasurementService;

import static ppj.vana.projekt.controller.ServerAPI.*;

//https://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/html/howto.html#howto-write-a-json-rest-service

@RestController
@RequestMapping(MEASUREMENT_BASE_PATH)
public class MeasurementRESTController {

    @Autowired
    private MongoMeasurementService measurementService;

    // GET - by ID 
    @RequestMapping(value = MEASUREMENT_NAME_PATH, method = RequestMethod.GET)
    public ResponseEntity<Measurement> getMeasurementByID(@PathVariable(MEASUREMENT_NAME) String id) {
        Measurement measurement = measurementService.getByID(id);
        if (measurement == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(measurement, HttpStatus.OK);
    }

    // DELETE - by ID (nazevStatu)
    @RequestMapping(value = MEASUREMENT_NAME_PATH, method = RequestMethod.DELETE)
    public ResponseEntity deleteMeasurement(@PathVariable(MEASUREMENT_NAME) String id) {
        Measurement measurement = measurementService.getByID(id);
        if (measurement == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        measurementService.remove(measurement);
        return new ResponseEntity(HttpStatus.OK);
    }

    // PUT - add Measurement
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity addMeasurement(@RequestBody Measurement measurement) {
        if (measurementService.exists(measurement.getId()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        measurementService.add(measurement);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // POST - update Measurement
    @RequestMapping(value = MEASUREMENT_NAME_PATH, method = RequestMethod.POST)
    public ResponseEntity updateMeasurement(@PathVariable(MEASUREMENT_NAME) String id, @RequestBody Measurement measurement) {
        // Check if updated measurement exists
        if (!measurementService.exists(id))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        // check if updated measurement name (ID) matches requested ID
        Measurement measurementLoaded = measurementService.getByID(id);
        if (measurementLoaded == null || !measurementLoaded.getId().equals(measurement.getId()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        measurementService.update(measurement);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler(APIException.class)
    public ResponseEntity<APIErrorMessage> handleAPIException(APIException ex) {
        return new ResponseEntity<>(new APIErrorMessage(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

}
