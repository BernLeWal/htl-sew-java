package server.sockets.echo;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * EchoServer is the server of the EchoServer-Demo
 * The server listens for incomming connections from new clients and
 * delegates the newly created socket for a client to an extra EchoService thread to be answered.
 *
 * Run an instance of this before starting any clients.
 */
public class EchoServer {
    public static void main(String[] args) {
        ServerSocket listener;
        try {
            listener = new ServerSocket(23);
            try {
                while (true) {
                    System.out.println("EchoServer is running, waiting for incoming on "
                            + InetAddress.getLocalHost().getHostAddress()
                            + ":" + listener.getLocalPort());
                    Socket serviceSocket = listener.accept();
                    EchoService service = new EchoService(serviceSocket);
                    service.start();
                }
            } finally {
                listener.close();
            }
        } catch (IOException e) {
            System.err.println("EchoServer: ERROR " + e);
        }
    }
}
