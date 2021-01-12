package server.http;

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
public class MiniWebServer {
    public static final String WWW_FILES_BASE_PATH = "/www";

    private static int thread_count = 0;

    public static void main(String[] args) {
        System.out.println("MiniWebServer started...");
        try (ServerSocket listener = new ServerSocket(80)) {
            while (true) {
                System.out.println("MiniWebServer is running, waiting for incoming on "
                        + InetAddress.getLocalHost().getHostAddress()
                        + ":" + listener.getLocalPort());
                Socket serviceSocket = listener.accept();

                Thread service = new Thread() {
                    @Override
                    public void run() {
                        int thread_nr = ++thread_count;
                        System.out.println("MiniWebServer Client-Thread" + thread_nr + ": waiting for input from the client...");
                        try {
                            // read request
                            HttpRequest request = new HttpRequest(serviceSocket.getInputStream());
                            String subPage = request.getPath();
                            if (subPage.isEmpty() || subPage.equalsIgnoreCase("/")) {
                                subPage = "/index.html";
                            }

                            HttpResponse response = new HttpResponse(serviceSocket.getOutputStream());
                            response.getHeaders().put("Server", "MiniWebServer 1.0");
                            response.sendFile(WWW_FILES_BASE_PATH + subPage );

                            serviceSocket.close();
                        } catch (IOException e) {
                            System.err.println("MiniWebServer Client-Thread" + thread_nr + " stopped with error " + e);
                        }
                        System.out.println("MiniWebServer Client-Thread" + thread_nr + " stopped.");
                    }
                };
                service.start();
            }
        } catch (IOException e) {
            System.err.println("MiniWebServer stopped with error: " + e);
        }
        System.out.println("MiniWebServer stopped.");
    }

}
