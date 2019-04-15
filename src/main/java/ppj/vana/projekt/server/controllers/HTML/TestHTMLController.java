package ppj.vana.projekt.server.controllers.HTML;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ppj.vana.projekt.data.City;
import ppj.vana.projekt.data.Measurement;
import ppj.vana.projekt.server.controllers.exceptions.APIErrorMessage;
import ppj.vana.projekt.server.controllers.exceptions.APIException;
import ppj.vana.projekt.service.CityService;
import ppj.vana.projekt.service.MongoMeasurementService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


// GUT TUTORIAL https://www.baeldung.com/spring-mvc-form-tutorial

@Controller
public class TestHTMLController {

    @Autowired
    private MongoMeasurementService measurementService;
    @Autowired
    private CityService cityService;


    @RequestMapping("/testPage")
    public String showMeasurements(Model model) {
        model.addAttribute("employee", new Employee());
        List<String> citiesStringList = new ArrayList<>();
        cityService.getAll().forEach((country) -> citiesStringList.add(country.getName() + " \t(" + country.getCountry().getName() + ")"));
        model.addAttribute("list", citiesStringList);
        return "testPage";
    }

    @RequestMapping(value = "/employee", method = RequestMethod.GET)
    public ModelAndView showForm() {
        return new ModelAndView("testPage", "employee", new Employee());
    }

    @RequestMapping(value = "/addEmployee", method = RequestMethod.POST)
    public String submit(@Valid @ModelAttribute("employee")Employee employee,
                         BindingResult result, ModelMap model) {
        if (result.hasErrors()) {
            return "error";
        }
        model.addAttribute("name", employee.getName());
        return "testPage";
    }

    @ExceptionHandler(APIException.class)
    public ResponseEntity<APIErrorMessage> handleAPIException(APIException ex) {
        return new ResponseEntity<>(new APIErrorMessage(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    public class Employee {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
