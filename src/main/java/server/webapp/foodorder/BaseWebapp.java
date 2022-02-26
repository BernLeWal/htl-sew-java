package server.webapp.foodorder;

import java.sql.*;

public abstract class BaseWebapp {
    // constants define the database-server, -driver and datasource
    protected final String jdbcDriver;
    protected final String databaseName;
    protected final Connection connection;

    public BaseWebapp(String jdbcDriver, String databaseName) throws ClassNotFoundException, SQLException {
        this.jdbcDriver = jdbcDriver;
        this.databaseName = databaseName;

        // load the sqlite-JDBC driver using the current class loader
        Class.forName(jdbcDriver);

        // create a database connection
        connection = DriverManager.getConnection("jdbc:sqlite:src/main/" + databaseName);
    }

    public void setup() {
        System.out.println("Setup " + jdbcDriver + " database in " + databaseName);

        recreateTableSpeisen();
        recreateTableBestellungen();
    }

    protected void recreateTableSpeisen() {
        System.out.println("Recreate table speisen");

        try (
                Statement statement = connection.createStatement()
        ) {
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            statement.executeUpdate("drop table if exists speisen");
            statement.executeUpdate("""
                    create table speisen (
                        id integer,
                        name string,
                        kategorie string,
                        preis real
                    )
                    """);
        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        }
    }

    protected void recreateTableBestellungen() {
        System.out.println("Recreate table bestellungen");

        try (
                Statement statement = connection.createStatement()
        ) {
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            statement.executeUpdate("drop table if exists bestellungen");
            statement.executeUpdate("""
                    create table bestellungen (
                        id integer,
                        name string,
                        anzahl string,
                        preis real
                    )
                    """);
        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        }
    }


    public int getNextOrderId() {
        try (
                Statement statement = connection.createStatement()
        ) {
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            // fetch max id-number
            int id = 1;
            ResultSet rs = statement.executeQuery("select max(id) from bestellungen");
            while (rs.next()) {
                // read the result set
                id = rs.getInt(1) + 1;
            }
            return id;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return 0;
        }
    }

    public int removeOrder(int id) {
        try (
                Statement statement = connection.createStatement()
        ) {
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            System.out.println("Remove bestellung " + id);
            return statement.executeUpdate("delete from bestellungen where id=" + id);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return -1;
        }
    }
}
