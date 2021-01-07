package server.sockets.telnetWithCHAP;

import utils.HashUtils;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

/**
 * TelnetWithCHAPServer is the server of the TelnetServer-Demo
 * The server listens for incoming connections from new clients and
 * delegates the newly created socket for a client to an extra TelnetService thread to be answered.
 * <p>
 * Run an instance of this before starting any clients.
 *
 * For authentication the CHAP (challenge-authentication-protocol) is implemented.
 * The users are hard-coded, use java/class or admin/1234.
 * ATTENTION: the commands entered will be executed in a command-shell (cmd.exe) on the server!
 */
public class TelnetWithCHAPServer {

    private HashMap<String, String> users = new HashMap<>();

    public TelnetWithCHAPServer() {
        // this should be external, f.e. in a database
        users.put("java", HashUtils.hash("class"));
        users.put("admin", HashUtils.hash("1234"));
    }

    public void run() {
        ServerSocket listener;
        try {
            listener = new ServerSocket(23);
            try {
                while (true) {
                    System.out.println("TelnetServer is running, waiting for incoming on " + InetAddress.getLocalHost().getHostAddress() + ":" + listener.getLocalPort());
                    Socket serviceSocket = listener.accept();
                    new TelnetWithCHAPService(this, serviceSocket);
                }
            } finally {
                listener.close();
            }
        } catch (Exception e) {
            System.err.println("TelnetServer: ERROR " + e);
        }
    }

    public String getPasswordHashFromLoginName(String loginName) {
        return users.get(loginName);
    }

    public static void main(String[] args) {
        TelnetWithCHAPServer server = new TelnetWithCHAPServer();
        server.run();
    }

}
