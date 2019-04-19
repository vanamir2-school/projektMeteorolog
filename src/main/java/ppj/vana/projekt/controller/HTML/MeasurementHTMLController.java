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
import ppj.vana.projekt.model.City;
import ppj.vana.projekt.model.Measurement;
import ppj.vana.projekt.providers.ContextProvider;
import ppj.vana.projekt.service.CityService;
import ppj.vana.projekt.service.MongoMeasurementService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class MeasurementHTMLController {

    @Autowired
    private MongoMeasurementService measurementService;
    @Autowired
    private CityService cityService;

    @RequestMapping("/printMeasurements")
    public String showMeasurements(Model model) {
        List<String> measurementStringList = new ArrayList<>();
        List<Measurement> measurementList = measurementService.getAll();
        Map<Integer, City> mapIdToCity = ContextProvider.getContext().getBean(CityService.class).getIdToCityMap();
        measurementList.forEach((measurement) -> measurementStringList.add(measurement.toStringReadable(mapIdToCity)));
        model.addAttribute("measurements", measurementStringList);
        return "printMeasurements";
    }

    @ExceptionHandler(APIException.class)
    public ResponseEntity<APIErrorMessage> handleAPIException(APIException ex) {
        return new ResponseEntity<>(new APIErrorMessage(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
