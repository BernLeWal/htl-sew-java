package server.webapp.foodorder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class YourBurgerWebapp extends BaseWebapp {
    // constants define the database-server, -driver and datasource
    public static final String JDBC_DRIVER = "org.sqlite.JDBC";
    public static final String DATABASE_NAME = "resources/server/webapp/foodorder/YourBurger.db";

    public YourBurgerWebapp() throws ClassNotFoundException, SQLException {
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
                    (1,	'Burger_Bun',   'BUN', 	    1.50),
                    
                    (2,	'Gurkamole',    'SOSSE', 	1.00),
                    (3,	'Burgersoße',   'SOSSE', 	1.00),
                    (4,	'Ketchup',      'SOSSE', 	0.50),
                    (5,	'Mayonaise',    'SOSSE', 	0.50),

                    (6,	'Rindfleisch',      'FLEISCH',	2.00),
                    (7,	'Rind_und_Cheddar', 'FLEISCH', 	2.50),
                    (8,	'Speck',            'FLEISCH', 	0.50),

                    (9, 'Salat',        'VEGI', 	2.00),
                    (10,'Tomaten',      'VEGI', 	2.00),
                    (11,'Paprika',      'VEGI', 	2.00),
                    (12,'Zwiebel',      'VEGI', 	1.00);
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

    public static void main(String[] args) {
        try {
            var webApp = new YourBurgerWebapp();
            webApp.setup();
            // TODO webApp.testTableBestellungen();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
