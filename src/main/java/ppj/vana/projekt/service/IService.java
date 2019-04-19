package ppj.vana.projekt.service;

import java.util.List;

public interface IService<E, Key> {

    // add or save entity
    void add(E entity);

    E get(Key id);

    void delete(E entity);

    boolean exists(E entity);

    List<E> getAll();

    void deleteAll();

    long count();
}
