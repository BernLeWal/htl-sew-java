package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * Helper functions to read CSV files.
 */
public class CsvUtils {
    public static ArrayList<ArrayList<String>> read(InputStream input, char separator) throws IOException {
        ArrayList<ArrayList<String>> list = new ArrayList<>();

        // first line is the header (we already know and store for debugging purpose)
        BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
        var header = reader.readLine();
        System.out.println("CSV-header: " + header);

        boolean isContentOver = false;
        while (!isContentOver) {
            ArrayList<String> line = new ArrayList<>();

            boolean isLineOver = false;
            while (!isLineOver && !isContentOver) {
                StringBuilder readPart = new StringBuilder();

                boolean isPartOver = false;
                while (!isPartOver) {
                    int i = reader.read();
                    if (i == -1) {
                        isPartOver = true;
                        isContentOver = true;
                    } else {
                        char character = (char) i;
                        if (character == separator) {
                            isPartOver = true;
                        } else if (character == '\r' || character == '\n') {
                            reader.mark(1);
                            char lineFeed = (char) reader.read();
                            if (lineFeed != '\n') {
                                reader.reset(); // equivalent for peek (in C#)
                            }

                            isPartOver = true;
                            isLineOver = true;
                        } else if (character == '\"') {
                            do {
                                character = (char) reader.read();
                                if (character == (char)-1) {
                                    isPartOver = true;
                                    isContentOver = true;
                                    break;
                                } else if (character == '\"') {
                                    break;
                                } else {
                                    readPart.append(character); // because character is of type int
                                }
                            } while (character != '\"');
                        } else {
                            readPart.append(character);
                        }
                    }
                }
                line.add(readPart.toString());
            }

            if (line.size() > 1) {
//                System.out.println(line);
                list.add(line);
            }
        }

        System.out.printf("Read %d items from CSV-file.\n", list.size());
        return list;
    }
}
