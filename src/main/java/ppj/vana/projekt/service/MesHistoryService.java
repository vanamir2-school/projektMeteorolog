package ppj.vana.projekt.service;

import org.springframework.stereotype.Service;
import ppj.vana.projekt.model.MesHistory;
import ppj.vana.projekt.model.repository.MesHistoryRepository;

@Service
public class MesHistoryService {

    private final MesHistoryRepository repository;

    public MesHistoryService(MesHistoryRepository repository) {
        this.repository = repository;
    }

    public void add(MesHistory mesHistory) {
        repository.save(mesHistory);
    }

    public Long count() {
        return repository.count();
    }

    public MesHistory getLatest() {
        return repository.getLatestMes();
    }

    public void deleteAll() {
        repository.deleteAll();
    }

}
