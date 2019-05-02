package ppj.vana.projekt.controller.HTML;

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

    private static final String URL_MEASUREMENT_BY_COUNTRY = "measurementByCountry";
    private static final String ATTR_SELECT_COUNTRY = "selectCountry";
    private static final String ATTR_COUNTRY_LIST = "countryList";
    private static final String ATTR_MEASUREMENT_LIST = "measurementList";

    private final MeasurementService measurementService;
    private final CityService cityService;
    private final CountryService countryService;
    private List<String> countryStringList = new ArrayList<>();

    public MeasurementByCountryHTMLController(MeasurementService measurementService, CityService cityService, CountryService countryService) {
        this.measurementService = measurementService;
        this.cityService = cityService;
        this.countryService = countryService;
    }

    @RequestMapping("/" + URL_MEASUREMENT_BY_COUNTRY)
    public String showMeasurements(Model model) {
        model.addAttribute(ATTR_SELECT_COUNTRY, "Select country to list all of its measurements.");
        countryStringList.clear();
        countryService.getAll().forEach((country) -> countryStringList.add(country.getName()));
        model.addAttribute(ATTR_COUNTRY_LIST, countryStringList);
        model.addAttribute(ATTR_MEASUREMENT_LIST, new ArrayList<Measurement>());
        return URL_MEASUREMENT_BY_COUNTRY;
    }

    // Return List of Measurements for all cities in selected country.
    // final List is sorted by cityName and timeOfMeasurement
    @RequestMapping(value = "/confirmCountry", method = RequestMethod.POST)
    public String submit(@Valid @ModelAttribute("country") Country country, BindingResult result, ModelMap model) {
        if (result.hasErrors())
            throw new IllegalArgumentException("There was an error." + result.getFieldError().toString());
        String selectedCountry = country.getName();

        if(countryStringList.indexOf(selectedCountry) == -1)
            throw new IllegalArgumentException("Selected country is not in DB.");

        // set selected country to be first value of the select combo
        Collections.swap(countryStringList, 0, countryStringList.indexOf(selectedCountry));

        // add values to model for jsp
        model.addAttribute(ATTR_SELECT_COUNTRY, country.getName());
        model.addAttribute(ATTR_COUNTRY_LIST, countryStringList);

        // all cities in country
        List<City> cityListByCounty = cityService.getCitiesByCountry(selectedCountry);
        if (cityListByCounty == null || cityListByCounty.isEmpty()) {
            model.addAttribute(ATTR_MEASUREMENT_LIST, new ArrayList<Measurement>());
            return URL_MEASUREMENT_BY_COUNTRY;
        }
        // all IDs of selected cities
        List<Integer> citiesID = new ArrayList<>();
        cityListByCounty.forEach((c) -> citiesID.add(c.getOpenWeatherMapID()));
        // all measurements for selected citites - object Measurement
        List<Measurement> measurementList = measurementService.findAllRecordForCities(citiesID);
        measurementList.sort(Comparator.comparing(Measurement::getCityID).thenComparing(Measurement::getTimeOfMeasurement));

        model.addAttribute(ATTR_MEASUREMENT_LIST, measurementList);
        return URL_MEASUREMENT_BY_COUNTRY;
    }

}
