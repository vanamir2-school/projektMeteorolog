package ppj.vana.projekt.repositories;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ppj.vana.projekt.data.City;

@Repository
public interface CityRepository extends CrudRepository<City, String> {
}
