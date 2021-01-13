package server.webapp.macdagoberts;

import java.sql.*;

/**
 * MacdagobertsWebappSetup initialized the SQLite database used in the MacDagobert's webapp.
 * Remarks: Run this program before starting the webapp!
 */
public class MacdagobertsWebappSetup {
    public static void main(String[] args) throws ClassNotFoundException {
        System.out.println("Setup SQLite database in resources/server/webapp/macdagoberts/MacDagoberts.db");

        // load the sqlite-JDBC driver using the current class loader
        Class.forName("org.sqlite.JDBC");

        try (// create a database connection
             Connection connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/server/webapp/macdagoberts/MacDagoberts.db");
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
                System.out.printf("Speise: id=%02d, name='%s', kategorie='%s', preis=%f\n",
                        rs.getInt("id"), rs.getString("name"), rs.getString("kategorie"), rs.getFloat("preis") );
            }
        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        }

    }
}
