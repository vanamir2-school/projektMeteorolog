package ppj.vana.projekt.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static ppj.vana.projekt.service.UtilService.TRANSACTION_TIMEOUT;

@Service
@Transactional(timeout = TRANSACTION_TIMEOUT)
public interface IService<E, Key> {

    // add or save entity
    void add(E entity);

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    E get(Key id);

    void delete(E entity);

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    boolean exists(E entity);

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    List<E> getAll();

    void deleteAll();

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    long count();
}
