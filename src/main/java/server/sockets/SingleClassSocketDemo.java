/*
 * Ein einfaches Beispiel zur Demonstration einer Java-Anwendung, die gleichzeitig Server und Client ist.
 * Der Client verbindet sich zum Server, der Server sendet "Hello World" zurück.
 *
 * Weiterführende Infos:
 * http://stackoverflow.com/questions/15541804/creating-the-serversocket-in-a-separate-thread
 */
package server.sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 * SingleClassSocketDemo is Server and Client at the same time
 * using an extra thread for the server.
 */
public class SingleClassSocketDemo {

    public static void main(String[] args) throws IOException {
        // WALL: Hier wird der Server im eigenen Thread gestartet - die Funktion kehrt sofort zurück
        new SingleClassSocketDemo().startServer();

        // WALL: Hier läuft der Client {{
        Socket s = new Socket("127.0.0.1", 9090);   // WALL: localhost:9090
        BufferedReader input
                = new BufferedReader(new InputStreamReader(s.getInputStream()));
        String answer = input.readLine();
        JOptionPane.showMessageDialog(null, answer);
        System.exit(0);
        // }} Client Ende.
    }

    public void startServer() {
        Runnable serverTask = new Runnable() {
            @Override
            public void run() {
                try {
                    try (ServerSocket listener = new ServerSocket(9090)) {
                        while (true) {
                            try (Socket socket = listener.accept()) {
                                // WALL Hier wird der Client-Request verarbeitet: {{
                                PrintWriter out
                                        = new PrintWriter(socket.getOutputStream(), true);
                                out.println("Hello World!");
                                // }} Client-Request Verarbeitung Ende.
                            }
                        }
                    }

                } catch (IOException e) {
                    System.err.println("Unable to process client request");
                    e.printStackTrace();
                }
            }
        };
        Thread serverThread = new Thread(serverTask);
        serverThread.start();
    }
}
