package core.exceptions;

/**
 * NumberFormatExceptionDemo shows the usage of try-catch
 * to handle NumberFormatExceptions in the process to convert
 * a strings content into a int-number.
 */
public class NumberFormatExceptionDemo {
    public static void main(String[] args) {
        int zahl;
        String umwandeln = "23%";
        try {
            // implementation under test:
            zahl = Integer.parseInt(umwandeln);
            System.out.println(zahl);
        } catch (NumberFormatException e) {
            // executed when an exception occurs
            System.out.println("Es können nur Zahlen nach int umgewandelt werden!");
            e.printStackTrace();
        } finally {
            // clean-up
            umwandeln = "";
        }
    }
}
