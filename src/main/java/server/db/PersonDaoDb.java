package server.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * PersonDaoDb implementats of the DAO-pattern with a SQLite database.
 * <p>
 * To use SQLite you need to download the SQLite JDBC-Connector,
 * f.e. from https://dbschema.com/jdbc-driver/Sqlite.html ,
 * place it into the /lib folder and add a JAR dependency
 * or add the following dependency in your Maven pom.xml:
 * <dependency>
 * <groupId>org.xerial</groupId>
 * <artifactId>sqlite-jdbc</artifactId>
 * <version>3.34.0</version>
 * </dependency>
 */
public class PersonDaoDb implements Dao<Person> {

    public static Connection connection;

    public PersonDaoDb() {
        try {
            // load the sqlite-JDBC driver using the current class loader
            Class.forName("org.sqlite.JDBC");

            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/server/db/JdbcDemo.db");

            // initialize the database
            try (Statement statement = connection.createStatement() ) {
                statement.executeUpdate("drop table if exists person");
                statement.executeUpdate("create table person (id integer primary key autoincrement , vorname string, nachname string, [alter] integer)");
                statement.executeUpdate("insert into person (vorname, nachname, [alter]) values('Rudi','Ratlos',43)");
                statement.executeUpdate("insert into person (vorname, nachname, [alter]) values('Susi','Sorglos',19)");
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public Person get(int id) {
        try ( PreparedStatement statement = connection.prepareStatement("select id, vorname, nachname, [alter] from person where id=?") ) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                // read the result set
                return new Person( rs.getString(2), rs.getString(3), rs.getInt(4) );
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Person> getAll() {
        List<Person> persons = new ArrayList<>();
        try ( PreparedStatement statement = connection.prepareStatement("select id, vorname, nachname, [alter] from person order by id") ) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                // read the result set
                persons.add( new Person( rs.getString(2), rs.getString(3), rs.getInt(4) ) );
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return persons;
    }

    @Override
    public int create(Person person) {
        try (PreparedStatement statement = connection.prepareStatement("insert into person (vorname, nachname, [alter]) values(?,?,?)", Statement.RETURN_GENERATED_KEYS) ) {
            statement.setString(1, person.vorname());
            statement.setString(2, person.nachname());
            statement.setInt( 3, person.alter());
            statement.executeUpdate();

            // return the generated id from the database (AUTOINCREMENT)
            ResultSet rs = statement.getGeneratedKeys();
            if( rs.next() )
                return rs.getInt(1);
            else
                return -1;  // unknown key value
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return 0;
        }
    }

    @Override
    public void update(int id, Person person) {
        try (PreparedStatement statement = connection.prepareStatement("update person set vorname=?, nachname=?, [alter]=? where id=?") ) {
            statement.setString(1, person.vorname());
            statement.setString(2, person.nachname());
            statement.setInt( 3, person.alter());
            statement.setInt(4, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        try (PreparedStatement statement = connection.prepareStatement("delete from person where id=?") ) {
            statement.setInt(1, id );
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
