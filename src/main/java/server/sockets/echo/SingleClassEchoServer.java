package server.sockets.echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * SingleClassEchoServer is the server of the EchoServer-Demo
 * The server listens for incomming connections from new clients and
 * delegates the newly created socket for a client to an extra thread to be answered.
 * All implemented in a single class.
 * <p>
 * Run an instance of this before starting any clients.
 */

public class SingleClassEchoServer {
    public static void main(String[] args) {

        try (ServerSocket listener = new ServerSocket(23)) {
            while (true) {
                System.out.println("EchoServer is running, waiting for incoming on "
                        + InetAddress.getLocalHost().getHostAddress()
                        + ":" + listener.getLocalPort());
                Socket serviceSocket = listener.accept();
                new Thread(() -> {
                    System.out.println("EchoService: waiting for input from the client...");
                    try (BufferedReader input = new BufferedReader(new InputStreamReader(serviceSocket.getInputStream()));
                         PrintStream output = new PrintStream(serviceSocket.getOutputStream())) {
                        while (true) {
                            String line = input.readLine();
                            if (line == null || line.equalsIgnoreCase("quit")) {
                                break;
                            }
                            System.out.println("EchoService: echo " + line);
                            output.println(line);
                        }
                    } catch (IOException ex) {
                        System.err.println("EchoService: ERROR " + ex);
                    }
                    System.out.println("EchoService: stopped.");
                }).start();
            }
        } catch (IOException e) {
            System.err.println("EchoServer: ERROR " + e);
        }
    }

}
