package server.db;

import java.sql.*;

/**
 * JdbcDemo shows how to use a very simple database based on SQLite
 */
public class JdbcDemo {
    public static void main(String[] args) throws ClassNotFoundException {
        // load the sqlite-JDBC driver using the current class loader
        Class.forName("org.sqlite.JDBC");

        try (// create a database connection
             Connection connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/server/db/JdbcDemo.db");
             Statement statement = connection.createStatement()
        ) {
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            statement.executeUpdate("drop table if exists person");
            statement.executeUpdate("create table person (id integer, name string)");
            statement.executeUpdate("insert into person values(1, 'Anton')");
            statement.executeUpdate("insert into person values(2, 'Berta')");

            ResultSet rs = statement.executeQuery("select * from person");
            while (rs.next()) {
                // read the result set
                System.out.println("name = " + rs.getString("name"));
                System.out.println("id = " + rs.getInt("id"));
            }
        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        }
    }
}
