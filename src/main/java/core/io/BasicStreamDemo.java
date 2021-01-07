package core.io;

import java.io.IOException;

/**
 * BasicStreamDemo shows how to work with the basic InputStream and OutputStream
 * just by using the read() and write() methods
 * only capable of dealing with bytes and byte-arrays
 */
public class BasicStreamDemo {

    public static void main(String[] args) {
        byte[] buffer = new byte[80];
        String str = "";
        do {
            System.out.print("Eingabe>");
            System.out.flush();
            try {
                // Einlesen der Zeichen
                int read = System.in.read(buffer, 0, 80);

                // Umwandeln von byte-Array in String
                str = new String(buffer, 0, read);

                // Ausgeben der eingelesenen Zeichen
                System.out.write(buffer, 0, read);
                //System.out.print(str);
                System.out.flush();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } while (!str.equals("quit\n"));
    }
}
