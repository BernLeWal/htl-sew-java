package server.db;

import java.util.List;

/**
 * Dao<T> defines a basic DAO layer for the CRUD operations.
 * It keeps the domain model completely decoupled from the persistence layer.
 * @param <T> Class which is persisted
 */
public interface Dao<T> {
    T get(int id);              // Read (Retrieve) one specific instance (by id)
    List<T> getAll();           // Read (Retrieve) all instances
    int create(T t);            // Create, returns the new id
    void update(int id, T t);   // Update
    void delete(int id);        // Delete
}
