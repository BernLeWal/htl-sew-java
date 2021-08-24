package core.properties;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

/**
 * PropertiesDemo shows how to read properties from a .properties-file
 * (provided in the resources), change some property-values and write
 * it to a .properties-file stored in the current working directory.
 * <p>
 * key value pair extending Hashtable: see https://en.wikipedia.org/wiki/.properties
 */
public class PropertiesDemo {
    public static void main(String[] args) throws IOException {
        // read properties from app.properties file:
        // The (read-only) file from the resources is read
        Properties props = new Properties();
        props.load(Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("core/properties/app.properties"));

        System.out.printf("%s - %s\n", props.getProperty("name"), props.getProperty("version"));
        System.out.println(props.getProperty("message"));

        // change and write properties to current.app.properties file:
        // To store properties changed by the user (mustnot be in the resources)
        props.setProperty("lastUpdate", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(Calendar.getInstance().getTime()));

        var out = new FileOutputStream("current.app.properties");
        props.store(out, "my comment");
        out.close();
    }
}
