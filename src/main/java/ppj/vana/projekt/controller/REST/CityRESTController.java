package ppj.vana.projekt.controller.REST;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ppj.vana.projekt.controller.exceptions.APIErrorMessage;
import ppj.vana.projekt.controller.exceptions.APIException;
import ppj.vana.projekt.model.City;
import ppj.vana.projekt.service.CityService;
import ppj.vana.projekt.service.MeasurementService;

import javax.validation.constraints.NotNull;
import java.util.List;

import static ppj.vana.projekt.controller.ServerAPI.*;

@RestController
@RequestMapping(CITY_BASE_PATH)
public class CityRESTController {

    private final CityService cityService;

    private final MeasurementService measurementService;

    @NotNull
    @Value("${app.readonly}")
    private Boolean readonly;

    public CityRESTController(CityService cityService, MeasurementService measurementService) {
        this.cityService = cityService;
        this.measurementService = measurementService;
    }

    // GET - all cities
    @RequestMapping(value = CITY_ALL_PATH, method = RequestMethod.GET)
    public ResponseEntity<List<City>> getCities() {
        return new ResponseEntity<>(cityService.getAll(), HttpStatus.OK);
    }

    // GET - average by ID (cityName) X days back
    @RequestMapping(value = CITY_NAME_PATH + CITY_DAYS_PATH, method = RequestMethod.GET)
    public ResponseEntity<String> getAverageByCityByParam(@PathVariable(CITY_NAME) String cityName, @PathVariable(CITY_DAYS) Integer days) {
        String average = measurementService.averageValuesForCity(cityName, days);
        return new ResponseEntity<>(average, HttpStatus.OK);

    }

    // GET - by ID (cityName)
    @RequestMapping(value = CITY_NAME_PATH, method = RequestMethod.GET)
    public ResponseEntity<City> getCityByID(@PathVariable(CITY_NAME) String cityName) {
        if (!cityService.existsById(cityName))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        City city = cityService.get(cityName);
        return new ResponseEntity<>(city, HttpStatus.OK);
    }

    // DELETE - by ID (cityName)
    @RequestMapping(value = CITY_NAME_PATH, method = RequestMethod.DELETE)
    public ResponseEntity deleteCity(@PathVariable(CITY_NAME) String cityName) {
        if (readonly)
            return new ResponseEntity<>(READ_ONLY_ERROR, HttpStatus.METHOD_NOT_ALLOWED);
        City city = cityService.get(cityName);
        if (city == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        cityService.delete(city);
        return new ResponseEntity(HttpStatus.OK);
    }

    // PUT - add City
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity addCity(@RequestBody City city) {
        if (readonly)
            return new ResponseEntity<>(READ_ONLY_ERROR, HttpStatus.METHOD_NOT_ALLOWED);
        if (cityService.exists(city))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        cityService.add(city);
        return new ResponseEntity<>(city, HttpStatus.OK);
    }

    // POST - update City
    @RequestMapping(value = CITY_NAME_PATH, method = RequestMethod.POST)
    public ResponseEntity updateCity(@PathVariable(CITY_NAME) String cityName, @RequestBody City city) {
        if (readonly)
            return new ResponseEntity<>(READ_ONLY_ERROR, HttpStatus.METHOD_NOT_ALLOWED);
        // Check if updated city exists
        if (!cityService.existsById(cityName))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        // check if updated city name (ID) matches requested cityName
        City cityLoaded = cityService.get(cityName);
        if (cityLoaded == null || !cityLoaded.getName().equals(cityName))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        cityService.add(city);
        return new ResponseEntity<>(city, HttpStatus.OK);
    }

    @ExceptionHandler(APIException.class)
    public ResponseEntity<APIErrorMessage> handleAPIException(APIException ex) {
        return new ResponseEntity<>(new APIErrorMessage(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

}
