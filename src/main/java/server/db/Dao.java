package server.db;

import java.util.List;

/**
 * Dao<T> defines a basic DAO layer for the CRUD operations.
 * It keeps the domain model completely decoupled from the persistence layer.
 * @param <T> Class which is persisted
 */
public interface Dao<T> {
    T get(int id);
    List<T> getAll();
    int create(T t);
    void update(int id, T t);
    void delete(int id);
}
