package ppj.vana.projekt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ppj.vana.projekt.model.Country;
import ppj.vana.projekt.model.MesHistory;
import ppj.vana.projekt.model.repository.CountryRepository;
import ppj.vana.projekt.model.repository.MesHistoryRepository;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class MesHistoryService {

    @Autowired
    private MesHistoryRepository repository;

    public void add(MesHistory mesHistory){
        repository.save(mesHistory);
    }

    public Long count(){
        return repository.count();
    }

    public MesHistory getLatest(){
        return repository.getLatestMes();
    }

    public void deleteAll() {
        repository.deleteAll();
    }

}
