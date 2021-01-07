package core.io.text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * PrintStreamDemo shows how to work with strings in comfortable way provided by PrintWriter.
 */
public class PrintStreamDemo {
    public static void main(String[] args) {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out))) {
            String text;
            do {
                text = in.readLine();
                out.println(text);
                out.flush();
            } while (!text.equals("quit"));
        } catch (IOException ex) {
            Logger.getLogger(PrintStreamDemo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
