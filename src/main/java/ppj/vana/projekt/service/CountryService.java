package ppj.vana.projekt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ppj.vana.projekt.data.Country;
import ppj.vana.projekt.repositories.CountryRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CountryService {

    @Autowired
    private CountryRepository countryRepository;

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

    public Optional<Country> getById(String country) {
        return countryRepository.findById(country);
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
