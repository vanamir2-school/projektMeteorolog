package ppj.vana.projekt.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ppj.vana.projekt.model.City;
import ppj.vana.projekt.model.repository.CityRepository;
import ppj.vana.projekt.providers.ContextProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CityService implements IService<City, String> {

    private static final Logger logger = LoggerFactory.getLogger(CityService.class);
    // support map that holds openWeatherMapID and its City
    private static Map<Integer, City> mapIdToCity = new HashMap<>();
    // flag to indicate the need of refresh
    private static boolean updateMap = true;

    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public static String getCityById(Integer cityID) {
        City city = CityService.getIdToCityMap().get(cityID);
        if (city == null)
            throw new IllegalArgumentException("Method CityService.getCityById() recieved non-existing cityID.");
        return city.getName();
    }

    private static Map<Integer, City> getIdToCityMap() {
        if (updateMap) {
            mapIdToCity.clear();
            ContextProvider.getContext().getBean(CityService.class).getAll().forEach((city) -> mapIdToCity.put(city.getOpenWeatherMapID(), city));
            updateMap = false;
        }
        return CityService.mapIdToCity;
    }

    // ------------------------------------------------ PUBLIC METHODS
    @Transactional
    public void deleteCityById(String city) {
        cityRepository.deleteById(city);
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public List<City> getCitiesByCountry(String countryName) {
        if (countryName == null) {
            String errorText = "Name of the country can not be null.";
            logger.error(errorText);
            throw new NullPointerException(errorText);
        } else if (countryName.isEmpty()) {
            String errorText = "Name of the country can't be empty.";
            logger.error(errorText);
            throw new IllegalArgumentException(errorText);
        }

        List<City> cityList = cityRepository.findByCountry(countryName);
        return cityList;
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public boolean existsById(String city) {
        return cityRepository.existsById(city);
    }

    // ------------------------------------------------ INTERFACE @Override

    @Override
    public void add(City city) {
        updateMap = true;
        cityRepository.save(city);
    }

    @Override
    public List<City> getAll() {
        return cityRepository.findAll();
    }

    @Override
    public void deleteAll() {
        cityRepository.deleteAll();
    }

    @Override
    public City get(String city) {
        if (cityRepository.findById(city).isPresent())
            return cityRepository.findById(city).get();
        return null;
    }

    @Override
    public long count() {
        return cityRepository.count();
    }

    @Override
    public void delete(City city) {
        updateMap = true;
        cityRepository.delete(city);
    }

    @Override
    public boolean exists(City entity) {
        return cityRepository.existsById(entity.getName());
    }

}
