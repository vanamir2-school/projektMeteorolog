package ppj.vana.projekt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ppj.vana.projekt.model.City;
import ppj.vana.projekt.model.repository.CityRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CityService implements IService<City, String> {

    @Autowired
    private CityRepository cityRepository;

    // support map that holds openWeatherMapID and its City
    private Map<Integer, City> mapIdToCity = new HashMap<>();

    // flag to indicate the need of refresh
    private boolean updateMap = true;

    public void deleteCityById(String city) {
        cityRepository.deleteById(city);
    }

    public List<City> getCitiesByCountry(String countryName) {
        if (countryName == null)
            return null;

        List<City> cityList = cityRepository.findByCountry(countryName);
        if (cityList.size() == 0)
            return cityList;
        return cityList;
    }

    public boolean existsById(String city) {
        return cityRepository.existsById(city);
    }

    public Map<Integer, City> getIdToCityMap() {
        if (updateMap) {
            mapIdToCity.clear();
            getAll().forEach((city) -> mapIdToCity.put(city.getOpenWeatherMapID(), city));
            updateMap = false;
        }
        return this.mapIdToCity;
    }

    // ------------------------------------------------ INTERFACE @Override

    @Override
    public void add(City city) {
        updateMap = true;
        cityRepository.save(city);
    }

    @Override
    public List<City> getAll() {
        return StreamSupport.stream(cityRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public void deleteAll() {
        cityRepository.deleteAll();
    }

    @Override
    public City get(String city) {
        if(cityRepository.findById(city).isPresent())
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
