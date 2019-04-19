package ppj.vana.projekt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ppj.vana.projekt.model.City;
import ppj.vana.projekt.model.Country;
import ppj.vana.projekt.model.repository.CountryRepository;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CountryService {

    // support map that is filled with HTTP request and used by Measurement entity to fill City name
    public static Map<Integer, City> mapIdToCity = new HashMap<>();

    @Autowired
    private CountryRepository countryRepository;

    public void create(Country country) {
        countryRepository.save(country);
    }

    public void save(Country country) {
        countryRepository.save(country);
    }

    public void saveList(List list) {
        countryRepository.saveAll(list);
    }

    public boolean exists(String country) {
        return countryRepository.existsById(country);
    }

    public List<Country> getAll() {
        return StreamSupport.stream(countryRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    public void deleteAll() {
        countryRepository.deleteAll();
    }

    public Optional<Country> getByName(String countryName) {
        return countryRepository.findById(countryName);
    }

    public Long count() {
        return countryRepository.count();
    }

    public void delete(Country country) {
        countryRepository.delete(country);
    }

    public void deleteById(String country) {
        countryRepository.deleteById(country);
    }
}
