package ppj.vana.projekt.server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ppj.vana.projekt.data.Country;
import ppj.vana.projekt.server.ServerAPI;
import ppj.vana.projekt.server.controllers.exceptions.APIErrorMessage;
import ppj.vana.projekt.server.controllers.exceptions.APIException;
import ppj.vana.projekt.service.CountryService;

import java.util.List;

@RestController
public class CountryController {

    @Autowired
    private CountryService countryService;

    @RequestMapping(value = ServerAPI.COUNTRY_PATH, method = RequestMethod.GET)
    public ResponseEntity<List<Country>> showCountries() {
        List<Country> countries = countryService.getAll();
        return new ResponseEntity<>(countries, HttpStatus.OK);
    }

    @ExceptionHandler(APIException.class)
    public ResponseEntity<APIErrorMessage> handleAPIException(APIException ex) {
        return new ResponseEntity<>(new APIErrorMessage(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

}
