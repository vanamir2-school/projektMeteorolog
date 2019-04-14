package ppj.vana.projekt.server.controllers.HTML;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import ppj.vana.projekt.data.City;
import ppj.vana.projekt.data.Measurement;
import ppj.vana.projekt.server.controllers.exceptions.APIErrorMessage;
import ppj.vana.projekt.server.controllers.exceptions.APIException;
import ppj.vana.projekt.service.CityService;
import ppj.vana.projekt.service.MongoMeasurementService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MeasurementHTMLController {

    @Autowired
    private MongoMeasurementService measurementService;

    // support map that is filled with HTTP request and used by Measurement entity to fill City name
    private static Map<Integer, City> mapIdToCity = new HashMap<>();
    @Autowired
    private CityService cityService;

    public static Map<Integer, City> getMapIdToCity(){
        return mapIdToCity;
    }

    @RequestMapping("/measurementByCountry")
    public String showMeasurements(Model model) {
        fillMap();
        List<String> measurementStringList = new ArrayList<>();
        List<Measurement> measurementList = measurementService.getAll();
        measurementList.forEach((measurement) -> measurementStringList.add(measurement.toStringReadable()));
        model.addAttribute("measurements", measurementStringList);
        return "measurementByCountry";
    }

    private void fillMap(){
        mapIdToCity.clear();
        cityService.getAll().forEach( (city)-> mapIdToCity.put(city.getOpenWeatherMapID(),city));
    }

    @ExceptionHandler(APIException.class)
    public ResponseEntity<APIErrorMessage> handleAPIException(APIException ex) {
        return new ResponseEntity<>(new APIErrorMessage(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
