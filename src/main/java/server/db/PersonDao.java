package server.db;

import java.util.ArrayList;
import java.util.List;

/**
 * The PersonDao class implements all the functionality required for fetching, updating, and removing Person objects.
 *
 * For simplicity's sake, the persons List acts like an in-memory database, which is populated with a couple of Person objects in the constructor.
 */
public class PersonDao implements Dao<Person>{

    private List<Person> persons = new ArrayList<>();

    public PersonDao() {
        persons.add(new Person("Rudi", "Ratlos", 43));
        persons.add(new Person( "Susi", "Sorglos", 19));
    }

    /**
     * Read the person by the given index number
     * @param index index of person, starts with 1
     * @return
     */
    @Override
    public Person get(int index) {
        if( index>0 && index<=persons.size() )
            return persons.get(index-1);
        else
            return null;
    }

    @Override
    /**
     * Read the complete list of all persons
     */
    public List<Person> getAll() {
        return persons;
    }

    /**
     * Creates a new person and persists it
     * @param person the given person object is stored in the list
     * @return index of the saved person, starts with 1
     */
    @Override
    public int create(Person person) {
        persons.add(person);
        return persons.size();
    }

    /**
     * Updates the person on the place of the index, with the given person
     * @param index index of person, starts with 1
     * @param person the person object with the updated data
     */
    @Override
    public void update(int index, Person person) {
        persons.set(index-1, person);
    }

    /**
     * Deletes a person from the list.
     * @param index index of person to be deleted, starts with 1
     */
    @Override
    public void delete(int index) {
        persons.remove(index-1);
    }
}
