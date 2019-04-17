package ppj.vana.projekt.controller.HTML;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import ppj.vana.projekt.controller.exceptions.APIErrorMessage;
import ppj.vana.projekt.controller.exceptions.APIException;
import ppj.vana.projekt.service.CountryService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CountryHTMLController {

    @Autowired
    private CountryService countryService;

    @RequestMapping("/printCountries")
    public String showOffers(Model model) {
        List<String> countriesStringList = new ArrayList<>();
        countryService.getAll().forEach((country) -> countriesStringList.add(country.getName()));
        model.addAttribute("countries", countriesStringList);
        return "printCountries";
    }

    @ExceptionHandler(APIException.class)
    public ResponseEntity<APIErrorMessage> handleAPIException(APIException ex) {
        return new ResponseEntity<>(new APIErrorMessage(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

}
