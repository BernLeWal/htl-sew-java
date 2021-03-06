package server.sockets.echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * EchoClient is a console-based client for the EchoServer in this samples
 */
public class EchoClient {

    public static void main(String[] args) {
        Socket clientSocket;
        try {
            clientSocket = new Socket("localhost", 23);
            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintStream output = new PrintStream(clientSocket.getOutputStream());

            Scanner scanner = new Scanner(System.in);
            System.out.println("EchoClient runs on " + clientSocket.getInetAddress().getHostAddress()
                    + ":" + clientSocket.getLocalPort() + " - type 'quit' to stop.");
            while (true) {
                // Daten von der Tastatur
                if( System.in.available() > 0  /*sind daten von scanner verfügbar? */ ) {
                    String line = scanner.nextLine();
                    output.println(line);
                    if (line.equalsIgnoreCase("quit")) {
                        break;
                    }
                }

                if( input.ready() /* sind Daten von input verfügbar? */ ) {
                    // Daten vom Server (Msgs von den anderen Clients)
                    String line = input.readLine();
                    System.out.println("EchoClient: recieved " + line);
                }
            }

            output.close();
            input.close();
            clientSocket.close();
        } catch (IOException e) {
            System.err.println("EchoClient: ERROR " + e);
        }
    }
}
