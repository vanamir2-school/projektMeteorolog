package ppj.vana.projekt.controller.REST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ppj.vana.projekt.model.City;
import ppj.vana.projekt.controller.exceptions.APIErrorMessage;
import ppj.vana.projekt.controller.exceptions.APIException;
import ppj.vana.projekt.service.CityService;
import ppj.vana.projekt.service.MongoMeasurementService;

import java.util.List;

import static ppj.vana.projekt.controller.ServerAPI.*;

@RestController
@RequestMapping(CITY_BASE_PATH)
public class CityRESTController {

    @Autowired
    private CityService cityService;

    @Autowired
    private MongoMeasurementService measurementService;

    // GET - všechny města
    @RequestMapping(value = CITY_ALL_PATH, method = RequestMethod.GET)
    public ResponseEntity<List<City>> getCities() {
        return new ResponseEntity<>(cityService.getAll(), HttpStatus.OK);
    }

    // GET - average by ID (nazevMesta)
    @RequestMapping(value = CITY_NAME_PATH + CITY_DAYS_PATH, method = RequestMethod.GET)
    public ResponseEntity<String> getAverageByCityByParam(@PathVariable(CITY_NAME) String cityName, @PathVariable(CITY_DAYS) Integer days) {
        String average = measurementService.averageValuesForCity(cityName,days);
        return new ResponseEntity<>(average, HttpStatus.OK);

    }

    // GET - by ID (nazevMesta)
    @RequestMapping(value = CITY_NAME_PATH, method = RequestMethod.GET)
    public ResponseEntity<City> getCityByID(@PathVariable(CITY_NAME) String cityName) {
        City city = cityService.getByName(cityName).orElse(null);
        if (city == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(city, HttpStatus.OK);
    }

    // DELETE - by ID (nazevMesta)
    @RequestMapping(value = CITY_NAME_PATH, method = RequestMethod.DELETE)
    public ResponseEntity deleteCity(@PathVariable(CITY_NAME) String cityName) {
        City city = cityService.getByName(cityName).orElse(null);
        if (city == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        cityService.delete(city);
        return new ResponseEntity(HttpStatus.OK);
    }

    // PUT - add City
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity addCity(@RequestBody City city) {
        if (cityService.exists(city.getName()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        cityService.save(city);
        return new ResponseEntity<>(city, HttpStatus.OK);
    }

    // POST - update City
    @RequestMapping(value = CITY_NAME_PATH, method = RequestMethod.POST)
    public ResponseEntity updateCity(@PathVariable(CITY_NAME) String cityName, @RequestBody City city) {
        // Check if updated city exists
        if (!cityService.exists(cityName))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        // check if updated city name (ID) matches requested cityName
        City cityLoaded = cityService.getByName(cityName).orElse(null);
        if (cityLoaded == null || !cityLoaded.getName().equals(cityName))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        cityService.save(city);
        return new ResponseEntity<>(city, HttpStatus.OK);
    }

    @ExceptionHandler(APIException.class)
    public ResponseEntity<APIErrorMessage> handleAPIException(APIException ex) {
        return new ResponseEntity<>(new APIErrorMessage(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

}
