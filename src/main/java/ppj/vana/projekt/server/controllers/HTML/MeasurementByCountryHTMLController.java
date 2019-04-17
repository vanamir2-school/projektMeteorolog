package ppj.vana.projekt.server.controllers.HTML;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ppj.vana.projekt.data.City;
import ppj.vana.projekt.data.Country;
import ppj.vana.projekt.data.Measurement;
import ppj.vana.projekt.service.CityService;
import ppj.vana.projekt.service.CountryService;
import ppj.vana.projekt.service.MongoMeasurementService;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class MeasurementByCountryHTMLController {

    @Autowired
    private MongoMeasurementService measurementService;
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

        fillMap();
        List<String>  measurementStringList = new ArrayList<>();
        // all cities in country
        List<City> cityListByCounty = cityService.getCitiesByCountry(selectedCountry);
        if(cityListByCounty == null){
            measurementStringList.add("No data available.");
            model.addAttribute("measurementList",measurementStringList);
            return "measurementByCountry";
        }
        // all IDs of selected cities
        List<Integer> citiesID = new ArrayList<>();
        cityListByCounty.forEach((c) -> citiesID.add(c.getOpenWeatherMapID()));
        // all measurements for selected citites - object Measurement
        List<Measurement> measumenetList = measurementService.findAllRecordForCities(citiesID);
        // all measurements for selected citites - String
        measumenetList.forEach( (m)->measurementStringList.add(m.toStringReadable() ));

        model.addAttribute("measurementList", measurementStringList.isEmpty() ? measurementStringList.add("No data available.") : measurementStringList);
        return "measurementByCountry";
    }

    private void fillMap() {
        CountryService.mapIdToCity.clear();
        cityService.getAll().forEach((city) -> CountryService.mapIdToCity.put(city.getOpenWeatherMapID(), city));
    }

}
