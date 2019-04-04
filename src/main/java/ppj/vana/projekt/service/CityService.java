package ppj.vana.projekt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ppj.vana.projekt.data.City;
import ppj.vana.projekt.repositories.CityRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    public void save(City city) {
        cityRepository.save(city);
    }

    public boolean exists(String city) {
        return cityRepository.existsById(city);
    }

    public List<City> getAllCities() {
        return StreamSupport.stream(cityRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    public void deleteAllCities() {
        cityRepository.deleteAll();
    }

    public void countCities() {
        cityRepository.count();
    }

    public void deleteCity(City city) {
        cityRepository.delete(city);
    }

    public void deleteCityById(String city) {
        cityRepository.deleteById(city);
    }
}
