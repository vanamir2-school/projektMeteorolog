package ppj.vana.projekt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ppj.vana.projekt.model.City;
import ppj.vana.projekt.model.Country;
import ppj.vana.projekt.model.repository.CountryRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static ppj.vana.projekt.service.UtilService.TRANSACTION_TIMEOUT;

@Service
public class CountryService implements IService<Country, String> {

    @Autowired
    private CountryRepository countryRepository;

    @Transactional
    public void deleteById(String country) {
        countryRepository.deleteById(country);
    }

    @Transactional
    public void saveList(List list) {
        countryRepository.saveAll(list);
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public boolean existsById(String country) {
        return countryRepository.existsById(country);
    }


    // ------------------------------------------------ INTERFACE @Override
    @Override
    public void add(Country country) {
        countryRepository.save(country);
    }

    @Override
    public List<Country> getAll() {
        return StreamSupport.stream(countryRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public void deleteAll() {
        countryRepository.deleteAll();
    }

    @Override
    public Country get(String countryName) {
        if(countryRepository.findById(countryName).isPresent())
            return countryRepository.findById(countryName).get();
        return null;
    }

    @Override
    public long count() {
        return countryRepository.count();
    }

    @Override
    public void delete(Country country) {
        countryRepository.delete(country);
    }

    @Override
    public boolean exists(Country entity) {
        return countryRepository.existsById(entity.getName());
    }

}
