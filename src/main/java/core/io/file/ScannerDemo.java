package core.io.file;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Scanner;

/**
 * ScannerDemo demonstrates how to parse a files content with the Scanner class.
 */
public class ScannerDemo {

    public static void main(String[] args) {
        // Fetch the numbers.txt file from the resources
        File file = null;
        try {
            ScannerDemo app = new ScannerDemo();
            String fileName = "core/io/file/numbers.txt";
            file = new File(app.getClass().getClassLoader().getResource(fileName).toURI());
            System.out.println("Reading contents from " + file.getAbsolutePath());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        if (file != null) {
            Scanner scanner;
            try {
                scanner = new Scanner(file);
                int sum = 0;
                while (scanner.hasNextInt()) {
                    sum += scanner.nextInt();
                }
                scanner.close();
                System.out.println("Sum = " + sum);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
