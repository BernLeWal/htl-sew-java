package server.webapp.hello;

import java.sql.*;

/**
 * HelloWebappSetup initialized the SQLite database used in the articles.jsp page.
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

            createTableUsers(statement);
            createTableArticles(statement);
        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        }

    }

    private static void createTableUsers(Statement statement) throws SQLException {
        System.out.println("Recreate table users:");

        statement.executeUpdate("drop table if exists users");
        statement.executeUpdate("""
            create table users (
            id integer,
            name string,
            login string,
            password_hash string
            )
            """);

        statement.executeUpdate("""
            insert into users (id, name, login, password_hash) values
            (1, 'Administrator', 'admin', '21232f297a57a5a743894a0e4a801fc3'),
            (2, 'Java Programmer', 'java', 'a2f2ed4f8ebc2cbb4c21a29dc40ab61d'),
            (3, 'NoPwd User', 'nopwd', 'd41d8cd98f00b204e9800998ecf8427e');
            """);

        ResultSet rs = statement.executeQuery("select * from users");
        while (rs.next()) {
            // read the result set
            System.out.printf("\tuser: id=%d, name='%s', login='%s', password_hash='%s'\n",
                    rs.getInt("id"), rs.getString("name"), rs.getString("login"), rs.getString("password_hash"));
        }

        System.out.println();
    }

    private static void createTableArticles(Statement statement) throws SQLException {
        System.out.println("Recreate table articles:");

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
            System.out.print("\tarticle: model = " + rs.getString("model") + ", ");
            System.out.println("id = " + rs.getInt("article_id"));
        }

        System.out.println();
    }
}
