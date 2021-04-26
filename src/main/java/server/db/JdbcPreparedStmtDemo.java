package server.db;

import java.sql.*;

/**
 * JdbcPreparedStmtDemo shows how to use prepared-statements to avoid SQL-injection
 * based on a simple PostgreSQL database.
 *
 * To use PostgreSQL you need to
 * (1) download and run a DB-engine, f.e. PostgreSQL-Portable
 *   f.e. from https://github.com/garethflowers/postgresql-portable/releases
 * (2) download the JDBC-Connector,
 *   f.e. from https://jdbc.postgresql.org/download.html ,
 *   place it into the /lib folder and add a JAR dependency
 * or add the following dependency in your Maven pom.xml:
 * <dependency>
 *   <groupId>org.postgresql</groupId>
 *   <artifactId>postgresql</artifactId>
 *   <version>42.2.18.jre7</version>
 * </dependency>
 */
public class JdbcPreparedStmtDemo {
    public static void main(String[] args) throws ClassNotFoundException {
        // load the sqlite-JDBC driver using the current class loader
        Class.forName("org.sqlite.JDBC");
        //initDatabase();

        try (// create a database connection
             Connection connection = DriverManager.getConnection(
                     "jdbc:postgresql://localhost:5432/jdbcdemo", "postgres", "");
             PreparedStatement statement = connection.prepareStatement(
                     "insert into person (id, name, age, description) values (?, ?, ?, ?)")
        ) {
            statement.setInt(1, 3);
            statement.setString(2, "Cäesar");
            statement.setInt(3, 33);
            statement.setString( 4, "Veni Vidi Vici");
            statement.executeUpdate();
        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        }
    }

    public static void initDatabase() {
        try (// create a database connection
             Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/jdbcdemo", "postgres", "");
             Statement statement = connection.createStatement()
        ) {
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            statement.executeUpdate("drop table if exists person");
            statement.executeUpdate("create table person (id INT, name VARCHAR(50), age INT, description VARCHAR(200) )");

            ResultSet rs = statement.executeQuery("select * from person");
            while (rs.next()) {
                // read the result set
                System.out.print("person: name = " + rs.getString("name"));
                System.out.println(", id = " + rs.getInt("id"));
            }
        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        }
    }
}
