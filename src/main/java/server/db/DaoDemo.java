package server.db;

/**
 * DaoDemo shows the implementation of the DAO-pattern with a "memory" and a database persistence layer.
 */
public class DaoDemo {
    public static void main(String[] args) {
        System.out.println("========== Persistence in the memory (using an ArrayList): ==========");
        crudDemos( new PersonDao() );

        System.out.println("========== Persistence in the database: ==========");
        crudDemos( new PersonDaoDb() );
    }

    private static void crudDemos(Dao<Person> personDao) {
        // read
        System.out.println(personDao.getAll().toString());

        Person person1 = personDao.get(1);
        System.out.println(person1);
        Person person2 = personDao.get(2);
        System.out.println(person2);
        Person person3 = personDao.get(3);
        System.out.println(person3);

        // create
        person3 = new Person("Peter", "Planlos", 30 );
        int personId = personDao.create(person3);
        System.out.println("Created new person with id " + personId);
        System.out.println(personDao.getAll().toString());

        // update
        person3 = new Person("Hans Peter", "Planlos", 30 );
        personDao.update(3, person3);
        System.out.println(personDao.getAll().toString());

        // delete
        personDao.delete(3);
        System.out.println(personDao.getAll().toString());
    }
}
