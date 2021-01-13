package server.webapp.hello;

import java.sql.*;

/**
 * HelloWebappSetup initialized the SQLite database used in the jstl_with_sql.jsp page.
 * Remarks: For a productive webapp it is better to create the database outside the web-pages.
 */
public class HelloWebappSetup {
    public static void main(String[] args) throws ClassNotFoundException {
        System.out.println("Setup SQLite database in resources/server/webapp/hello/HelloWebapp.db");

        // load the sqlite-JDBC driver using the current class loader
        Class.forName("org.sqlite.JDBC");

        try (// create a database connection
             Connection connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/server/webapp/hello/HelloWebapp.db");
             Statement statement = connection.createStatement()
        ) {
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            statement.executeUpdate("drop table if exists articles");
            statement.executeUpdate("""
                create table articles (
                    article_id integer,
                    producer string,
                    model string,
                    power_idle integer,
                    power_max integer,
                    benchmark integer,
                    price real
                )
                """);

            statement.executeUpdate("""
                insert into articles (article_id, producer, model, power_idle, power_max, benchmark, price) values
                (1, 'ASUS', 'Strix GTX 750Ti', 8, 56, 4030, 115),
                (2, 'AMD', 'Radeon R9 270X', 10, 128, 5539, 150),
                (3, 'ASUS', 'Strix GTX 760', 13, 161, 5491, 180);
                """);

            ResultSet rs = statement.executeQuery("select * from articles");
            while (rs.next()) {
                // read the result set
                System.out.print("article: model = " + rs.getString("model") + ", ");
                System.out.println("id = " + rs.getInt("article_id"));
            }
        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        }

    }
}
