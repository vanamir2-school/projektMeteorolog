package ppj.vana.projekt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ppj.vana.projekt.model.City;
import ppj.vana.projekt.model.repository.CityRepository;
import ppj.vana.projekt.providers.ContextProvider;

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
    private static Map<Integer, City> mapIdToCity = new HashMap<>();

    // flag to indicate the need of refresh
    private static boolean updateMap = true;

    // ------------------------------------------------ PUBLIC METHODS
    @Transactional
    public void deleteCityById(String city) {
        cityRepository.deleteById(city);
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public List<City> getCitiesByCountry(String countryName) {
        if (countryName == null)
            return null;

        List<City> cityList = cityRepository.findByCountry(countryName);
        if (cityList.size() == 0)
            return cityList;
        return cityList;
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public boolean existsById(String city) {
        return cityRepository.existsById(city);
    }


    public static String getCityById(Integer cityID){
        City city = CityService.getIdToCityMap().get(cityID);
        if( city == null )
            throw new IllegalArgumentException( "Method CityService.getCityById() recieved non-existing cityID.");
        return city.getName();
    }

    // TODO - private
    private static Map<Integer, City> getIdToCityMap() {
        if (updateMap) {
            mapIdToCity.clear();
            ContextProvider.getContext().getBean(CityService.class).getAll().forEach((city) -> mapIdToCity.put(city.getOpenWeatherMapID(), city));
            updateMap = false;
        }
        return CityService.mapIdToCity;
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
