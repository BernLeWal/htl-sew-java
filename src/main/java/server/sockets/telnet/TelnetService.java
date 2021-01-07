package server.sockets.telnet;

import utils.ProcessUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * TelnetService is the part of the TelnetServer, which responds to the requests from one specific client.
 * ATTENTION: the commands entered will be executed in a command-shell (cmd.exe) on the server!
 */
class TelnetService extends Thread {

    Socket serviceSocket;
    String id;

    TelnetService(Socket serviceSocket) {
        this.serviceSocket = serviceSocket;
        id = serviceSocket.getInetAddress().getHostAddress() + ":" + serviceSocket.getPort();
        System.out.println("TelnetService(" + id + "): new service working for client from " + id + "...");
    }

    @Override
    public void run() {
        System.out.println("TelnetService(" + id + "): started...");

        try {
            try (BufferedReader input = new BufferedReader(new InputStreamReader(serviceSocket.getInputStream()));
                 PrintStream output = new PrintStream(serviceSocket.getOutputStream())
            ) {
                System.out.println("TelnetService(" + id + "): asking client for login and password...");
                boolean allow = false;
                while (!allow) {
                    output.print("Login:");
                    String loginName = input.readLine();
                    output.print("Password:");
                    String password = input.readLine();

                    if (loginName.equals("java") && password.equals("class")) {
                        output.println("Login successful.");
                        allow = true;
                    } else {
                        output.println("Login FAILED!");
                    }
                }

                System.out.println("TelnetService(" + id + "): login OK, asking for commands and execute them...");
                while (allow) {
                    output.print(">");
                    String command = input.readLine();

                    if (command.equals("quit")) {
                        allow = false;
                    } else {
                        try {
                            ProcessUtils.executeCmd(command, output);
                        } catch (IOException e) {
                            System.err.println("TelnetService(" + id + "): ERROR " + e);
                        }
                    }
                }
            }
            serviceSocket.close();
        } catch (IOException e) {
            System.err.println("TelnetService(" + id + "): ERROR " + e);
        }
        System.out.println("TelnetService(" + id + "): stopped.");
    }

}
