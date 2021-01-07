package core.io.text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * BufferedStreamDemo shows how to work with complete strings provided by BufferedReader and BufferedWriter
 */
public class BufferedStreamDemo {

    public static void main(String[] args) {
        String text;
        try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out))
        ) {
            do {
                text = in.readLine();
                out.write(text);
                out.newLine();
                out.flush();
            } while (!text.equals("quit"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
