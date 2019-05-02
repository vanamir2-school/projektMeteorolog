package ppj.vana.projekt.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ppj.vana.projekt.model.MesHistory;

@Repository
public interface MesHistoryRepository extends JpaRepository<MesHistory, Integer> {

    // JPA @Query tutorial https://www.baeldung.com/spring-data-jpa-query
    @Query(value = "SELECT * FROM MesHistory WHERE time = (SELECT max(time) FROM MesHistory)", nativeQuery = true)
    MesHistory getLatestMes();
}
