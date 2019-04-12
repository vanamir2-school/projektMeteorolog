package ppj.vana.projekt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ppj.vana.projekt.data.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, String> {
}
