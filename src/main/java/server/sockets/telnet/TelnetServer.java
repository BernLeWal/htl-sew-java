package server.sockets.telnet;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * TelnetServer is the server of the TelnetServer-Demo
 * The server listens for incoming connections from new clients and
 * delegates the newly created socket for a client to an extra TelnetService thread to be answered.
 * <p>
 * Run an instance of this before starting any clients.
 * <p>
 * Login/Password is hardcoded, use "java" and "class".
 * ATTENTION: the commands entered will be executed in a command-shell (cmd.exe) on the server!
 */
public class TelnetServer {

    public static void main(String[] args) {
        ServerSocket listener;
        try {
            listener = new ServerSocket(23);
            try {
                while (true) {
                    System.out.println("TelnetServer is running, waiting for incoming on " + InetAddress.getLocalHost().getHostAddress() + ":" + listener.getLocalPort());
                    Socket serviceSocket = listener.accept();
                    TelnetService service = new TelnetService(serviceSocket);
                    service.start();
                }
            } finally {
                listener.close();
            }
        } catch (Exception e) {
            System.err.println("TelnetServer: ERROR " + e);
        }
    }

}
