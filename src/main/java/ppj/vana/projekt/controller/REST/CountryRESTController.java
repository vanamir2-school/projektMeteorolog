package ppj.vana.projekt.controller.REST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ppj.vana.projekt.model.Country;
import ppj.vana.projekt.controller.exceptions.APIErrorMessage;
import ppj.vana.projekt.controller.exceptions.APIException;
import ppj.vana.projekt.service.CountryService;

import java.util.List;

import static ppj.vana.projekt.controller.ServerAPI.*;

@RestController
@RequestMapping(COUNTRY_BASE_PATH)
public class CountryRESTController {

    @Autowired
    private CountryService countryService;

    // GET - všechny státy JSON formát
    @RequestMapping(value = COUNTRY_ALL_PATH, method = RequestMethod.GET)
    public ResponseEntity<List<Country>> getCountries() {
        return new ResponseEntity<>(countryService.getAll(), HttpStatus.OK);
    }

    // GET - by ID (nazevStatu)
    @RequestMapping(value = COUNTRY_NAME_PATH, method = RequestMethod.GET)
    public ResponseEntity<Country> getCountryByID(@PathVariable(COUNTRY_NAME) String countryName) {
        Country country = countryService.get(countryName);
        if (country == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(country, HttpStatus.OK);
    }

    // DELETE - by ID (nazevStatu)
    @RequestMapping(value = COUNTRY_NAME_PATH, method = RequestMethod.DELETE)
    public ResponseEntity deleteCountry(@PathVariable(COUNTRY_NAME) String countryName) {
        Country country = countryService.get(countryName);
        if (country == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        countryService.delete(country);
        return new ResponseEntity(HttpStatus.OK);
    }

    // PUT - add Country
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity addCountry(@RequestBody Country country) {
        if (countryService.exists(country))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        countryService.add(country);
        return new ResponseEntity<>(country, HttpStatus.OK);
    }

    @ExceptionHandler(APIException.class)
    public ResponseEntity<APIErrorMessage> handleAPIException(APIException ex) {
        return new ResponseEntity<>(new APIErrorMessage(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

}
