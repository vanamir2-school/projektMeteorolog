package ppj.vana.projekt.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ppj.vana.projekt.data.City;

@Repository
public interface CityRepository extends JpaRepository<City, String> {

}
