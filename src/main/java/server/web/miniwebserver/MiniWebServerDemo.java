package server.web.miniwebserver;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * MiniWebServerDemo demonstrates how to write a simplistic HTTP server.
 * It just supports to deliver static text-based (like html, css, js)
 * and binary-based (like jpg,..) files in simple GET or POST requests.
 * It also does not evaluate query parameters and request headers.
 */
public class MiniWebServerDemo {

    public static void main(String[] args) {
        System.out.println("MiniWebServer started...");
        try {

            try (ServerSocket listener = new ServerSocket(8081)) {
                while (true) {
                    System.out.println("MiniWebServer is running, waiting for incoming on "
                            + InetAddress.getLocalHost().getHostAddress()
                            + ":" + listener.getLocalPort());
                    Socket serviceSocket = listener.accept();
                    MiniWebService service = new MiniWebService(serviceSocket);
                    service.start();
                }
            }
        } catch (IOException e) {
            System.err.println("MiniWebServer stopped with error: " + e);
        }
        System.out.println("MiniWebServer stopped.");
    }

}
