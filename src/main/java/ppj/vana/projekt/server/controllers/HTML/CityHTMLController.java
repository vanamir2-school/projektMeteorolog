package ppj.vana.projekt.server.controllers.HTML;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import ppj.vana.projekt.server.controllers.exceptions.APIErrorMessage;
import ppj.vana.projekt.server.controllers.exceptions.APIException;
import ppj.vana.projekt.service.CityService;
import ppj.vana.projekt.service.CountryService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CityHTMLController {

    @Autowired
    private CityService cityService;

    @RequestMapping("/printCities")
    public String showOffers(Model model) {
        List<String> citiesStringList = new ArrayList<>();
        cityService.getAll().forEach((country) -> citiesStringList.add(country.getName() + " \t(" + country.getCountry().getName() + ")"));
        model.addAttribute("cities", citiesStringList);
        return "printCities";
    }

    @ExceptionHandler(APIException.class)
    public ResponseEntity<APIErrorMessage> handleAPIException(APIException ex) {
        return new ResponseEntity<>(new APIErrorMessage(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
