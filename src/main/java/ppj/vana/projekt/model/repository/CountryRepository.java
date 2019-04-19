package ppj.vana.projekt.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ppj.vana.projekt.model.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, String> {


}
