package ppj.vana.projekt.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ppj.vana.projekt.data.Country;

@Repository
public interface CountryRepository extends CrudRepository<Country, String> {
}
