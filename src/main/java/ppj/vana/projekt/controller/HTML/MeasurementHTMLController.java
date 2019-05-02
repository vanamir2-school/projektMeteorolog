package ppj.vana.projekt.controller.HTML;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import ppj.vana.projekt.controller.exceptions.APIErrorMessage;
import ppj.vana.projekt.controller.exceptions.APIException;
import ppj.vana.projekt.model.Measurement;
import ppj.vana.projekt.service.MeasurementService;

import java.util.Comparator;
import java.util.List;

@Controller
public class MeasurementHTMLController {

    private final MeasurementService measurementService;

    public MeasurementHTMLController(MeasurementService measurementService) {
        this.measurementService = measurementService;
    }

    @RequestMapping("/printMeasurements")
    public String showMeasurements(Model model) {
        List<Measurement> measurementList = measurementService.getAll();
        measurementList.sort(Comparator.comparing(Measurement::getCityID).thenComparing(Measurement::getTimeOfMeasurement));
        model.addAttribute("measurements", measurementList);
        return "printMeasurements";
    }

    @ExceptionHandler(APIException.class)
    public ResponseEntity<APIErrorMessage> handleAPIException(APIException ex) {
        return new ResponseEntity<>(new APIErrorMessage(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
