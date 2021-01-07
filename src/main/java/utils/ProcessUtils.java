package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

/**
 * Helper methods to deal with processes.
 */
public class ProcessUtils {

    /**
     * ATTENTION: the commands entered will be executed in a command-shell (cmd.exe) on the server!
     */
    public static void executeCmd(String command, PrintStream output) throws IOException {
        Runtime rt = Runtime.getRuntime();
        BufferedReader input;
        Process p = rt.exec("cmd.exe /C " + command);
        input = new BufferedReader(new InputStreamReader(p.getInputStream()));

        String line;
        while ((line = input.readLine()) != null) {
            output.println(line);
        }
        input.close();
    }
}
