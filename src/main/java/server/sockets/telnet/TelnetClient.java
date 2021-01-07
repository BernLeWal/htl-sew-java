package server.sockets.telnet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * TelnetClient is a console-based client for the TelnetServer in this demo
 */
public class TelnetClient {

    public static void main(String[] args) throws Exception {
        try (Socket clientSocket = new Socket("localhost", 23)) {

            BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));

            try (BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 PrintStream output = new PrintStream(clientSocket.getOutputStream())
            ) {
                while (true) {
                    if (input.ready()) {
                        System.out.print((char) input.read());
                    }

                    if (scanner.ready()) {
                        output.print((char) scanner.read());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
