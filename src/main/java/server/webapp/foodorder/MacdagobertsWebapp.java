package server.webapp.foodorder;

import java.sql.*;

/**
 * MacdagobertsWebapp initialized the SQLite database used in the MacDagobert's webapp
 * and provides a singleton instance to access the datalayer functions.
 *
 * Remarks: Run getInstance().setup() before starting the webapp!
 */
public class MacdagobertsWebapp extends BaseWebapp {
    // constants define the database-server, -driver and datasource
    public static final String JDBC_DRIVER = "org.sqlite.JDBC";
    public static final String DATABASE_NAME = "resources/server/webapp/foodorder/MacDagoberts.db";

    //
    // singleton-pattern to retrieve the one and only instance for the webapp
    // late binding is used, so that the instance is only created when really used for the first time.
    //
    private static MacdagobertsWebapp instance;

    public static MacdagobertsWebapp getInstance() {
        if (instance==null) {
            try {
                instance = new MacdagobertsWebapp();
            } catch (ClassNotFoundException e) {
                System.err.println("ERROR: JDBC-driver count not be loaded! Install the driver for " + JDBC_DRIVER);
                e.printStackTrace();
            } catch (SQLException e) {
                System.err.println("ERROR: Datasource could not be opened! Check that file access is possible to " + DATABASE_NAME);
                e.printStackTrace();
            }
        }
        return instance;
    }


    private MacdagobertsWebapp() throws ClassNotFoundException, SQLException {
        super(JDBC_DRIVER, DATABASE_NAME);
    }

    @Override
    protected void recreateTableSpeisen() {
        super.recreateTableSpeisen();
        try (
                Statement statement = connection.createStatement()
        ) {
            statement.executeUpdate("""
                    insert into speisen (id, name, kategorie, preis) values
                    (1,	'Hamburger',   'SPEISE', 	1.00),
                    (2,	'Cheesburger', 'SPEISE', 	1.50),
                    (3,	'BigMac',      'SPEISE', 	2.50),
                    (4,	'McChicken',   'SPEISE', 	2.00),
                    (5,	'CrispyWrap',  'SPEISE', 	3.00),
                    (6,	'McNuggets',   'SPEISE', 	2.00),

                    (7,	'PommesGross', 'BEILAGE',	1.50),
                    (8,	'PommesKlein', 'BEILAGE', 	1.00),
                    (9,	'Ketchup',     'BEILAGE', 	0.50),
                    (10,'Gartensalat', 'BEILAGE', 	2.00),

                    (11,'CocaCola',    'TRINKEN', 	2.00),
                    (12,'Eistee',      'TRINKEN', 	2.00),
                    (13,'Sprite',      'TRINKEN', 	2.00),
                    (14,'Mineralwasser','TRINKEN', 	1.00),
                    (15,'Orangensaft', 'TRINKEN', 	1.50),
                    (16,'Apfelsaft',   'TRINKEN', 	1.50),

                    (17,'Apfeltasche', 'DESSERT', 	1.00),
                    (18,'Schokomuffin','DESSERT', 	1.50),
                    (19,'Donut',       'DESSERT', 	1.00),
                    (20,'ErdbeerEis',  'DESSERT', 	1.50),
                    (21,'SchokoEis',   'DESSERT', 	1.50),
                    (22,'Kakao',       'DESSERT', 	2.00),
                    (23,'Capuccino',   'DESSERT', 	2.00);                
                    """);

            ResultSet rs = statement.executeQuery("select * from speisen");
            while (rs.next()) {
                // read the result set
                System.out.printf("\tSpeise: id=%02d, name='%s', kategorie='%s', preis=%f\n",
                        rs.getInt("id"), rs.getString("name"), rs.getString("kategorie"), rs.getFloat("preis"));
            }
        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        }
    }

    private void testTableBestellungen() {
        System.out.println("Test table bestellungen");

        try (
                Statement statement = connection.createStatement()
        ) {
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            // fetch max id-number
            int id = getNextOrderId();
            System.out.println("\tNext id for bestellungen is " + id);

            // add a new order
            statement.executeUpdate(String.format("""
                    insert into bestellungen (id, name, anzahl, preis) values
                    (%d,'Hamburger',   1, 	1.00),
                    (%d,'PommesGross', 2,	1.50),
                    (%d,'CocaCola',    3, 	2.00),
                    (%d,'Capuccino',   4, 	2.00);               
                    """, id, id, id, id));

            // show the order
            ResultSet rs = statement.executeQuery("select * from bestellungen where id=" + id);
            while (rs.next()) {
                // read the result set
                System.out.printf("\tBestellung: id=%d, name='%s', anzahl=%d, preis=%f\n",
                        rs.getInt("id"), rs.getString("name"), rs.getInt("anzahl"), rs.getFloat("preis"));
            }

            // check if the next orderid is valid
            int nextId = getNextOrderId();
            System.out.println("\tNext id for bestellungen is " + nextId);

            // remove the order
            removeOrder(id);

            // check if the next orderid is valid
            nextId = getNextOrderId();
            System.out.println("\tNext id for bestellungen is " + nextId);

        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        }
    }


    public static void main(String[] args) {
        getInstance().setup();
        getInstance().testTableBestellungen();
    }
}
