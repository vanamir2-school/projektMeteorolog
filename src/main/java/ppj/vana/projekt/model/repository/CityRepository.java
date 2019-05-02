package ppj.vana.projekt.model.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ppj.vana.projekt.model.City;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, String> {

    @Query("SELECT c FROM City AS c WHERE c.country.name= ?1")
    List<City> findByCountry(String countryName);

}
