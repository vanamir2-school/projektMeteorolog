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
import ppj.vana.projekt.providers.ContextProvider;
import ppj.vana.projekt.service.CityService;
import ppj.vana.projekt.service.CountryService;
import ppj.vana.projekt.service.MongoMeasurementService;

import javax.validation.Valid;
import java.util.*;

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

        List<String>  measurementStringList = new ArrayList<>();
        // all cities in country
        List<City> cityListByCounty = cityService.getCitiesByCountry(selectedCountry);
        if(cityListByCounty == null || cityListByCounty.isEmpty() ){
            measurementStringList.add("No measurements available.");
            model.addAttribute("measurementList",measurementStringList);
            return "measurementByCountry";
        }
        // all IDs of selected cities
        List<Integer> citiesID = new ArrayList<>();
        cityListByCounty.forEach((c) -> citiesID.add(c.getOpenWeatherMapID()));
        // all measurements for selected citites - object Measurement
        List<Measurement> measumenetList = measurementService.findAllRecordForCities(citiesID);
        // all measurements for selected citites - String
        Map<Integer, City> mapIdToCity = ContextProvider.getContext().getBean(CityService.class).getIdToCityMap();
        measumenetList.forEach( (m)->measurementStringList.add(m.toStringReadable(mapIdToCity) ));

        model.addAttribute("measurementList", measurementStringList.isEmpty() ? measurementStringList.add("No model available.") : measurementStringList);
        return "measurementByCountry";
    }

}
