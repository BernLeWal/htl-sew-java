package server.sockets.echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * EchoService is the part of the EchoServer, which responds to the requests from one specific client.
 * It just sends back the text from the incoming request - like an echo.
 */
public class EchoService extends Thread {
    private final Socket serviceSocket;

    public EchoService(Socket serviceSocket) {
        this.serviceSocket = serviceSocket;
        System.out.println("EchoService: new service working for client from "
                + serviceSocket.getInetAddress().getHostAddress()
                + ":" + serviceSocket.getPort());
    }

    @Override
    public void run() {
        System.out.println("EchoService: waiting for input from the client...");
        try {
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
            }
            serviceSocket.close();
        } catch (IOException ex) {
            System.err.println("EchoService: ERROR " + ex);
        }
        System.out.println("EchoService: stopped.");
    }
}
