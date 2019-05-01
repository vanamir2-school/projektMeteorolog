package ppj.vana.projekt.controller.HTML;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ppj.vana.projekt.model.City;
import ppj.vana.projekt.model.Country;
import ppj.vana.projekt.model.Measurement;
import ppj.vana.projekt.model.MesHistory;
import ppj.vana.projekt.service.CityService;
import ppj.vana.projekt.service.CountryService;
import ppj.vana.projekt.service.MeasurementService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
public class MeasurementByCountryHTMLController {

    @Autowired
    private MeasurementService measurementService;
    @Autowired
    private CityService cityService;
    @Autowired
    private CountryService countryService;

    private List<String> countryStringList = new ArrayList<>();

    @RequestMapping("/measurementByCountry")
    public String showMeasurements(Model model) {
        model.addAttribute("selectCountry", "Select country to list all of its measurements.");
        model.addAttribute("selectedCountry", new Country());
        countryStringList.clear();
        countryService.getAll().forEach((country) -> countryStringList.add(country.getName()));
        model.addAttribute("countryList", countryStringList);
        model.addAttribute("measurementList", new ArrayList<Measurement>());
        return "measurementByCountry";
    }

    @RequestMapping(value = "/confirmCountry", method = RequestMethod.POST)
    public String submit(@Valid @ModelAttribute("country") Country country,
                         BindingResult result, ModelMap model) {
        if (result.hasErrors())
            return "error";
        String selectedCountry = country.getName();

        // set selected country to be first value of the select combo
        Collections.swap(countryStringList, 0, countryStringList.indexOf(selectedCountry));

        // add values to model for jsp
        model.addAttribute("selectCountry", country.getName());
        model.addAttribute("countryList", countryStringList);

        // all cities in country
        List<City> cityListByCounty = cityService.getCitiesByCountry(selectedCountry);
        if (cityListByCounty == null || cityListByCounty.isEmpty()) {
            model.addAttribute("measurementList", new ArrayList<Measurement>());
            return "measurementByCountry";
        }
        // all IDs of selected cities
        List<Integer> citiesID = new ArrayList<>();
        cityListByCounty.forEach((c) -> citiesID.add(c.getOpenWeatherMapID()));
        // all measurements for selected citites - object Measurement
        List<Measurement> measurementList = measurementService.findAllRecordForCities(citiesID);
        measurementList.sort(Comparator.comparing(Measurement::getCityID).thenComparing(Measurement::getTimeOfMeasurement));

        model.addAttribute("measurementList", measurementList);
        return "measurementByCountry";
    }

}
