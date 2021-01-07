package server.sockets.telnetWithCHAP;

import utils.HashUtils;
import utils.ProcessUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * TelnetWithCHAPService is the part of the TelnetServer, which responds to the requests from one specific client.
 * For authentication the CHAP (challenge-authentication-protocol) is implemented.
 * ATTENTION: the commands entered will be executed in a command-shell (cmd.exe) on the server!
 */
public class TelnetWithCHAPService extends Thread {

    TelnetWithCHAPServer server;
    Socket serviceSocket;
    String login;
    String password;

    String id;

    TelnetWithCHAPService(TelnetWithCHAPServer server, Socket serviceSocket) {
        this.server = server;
        this.serviceSocket = serviceSocket;

        id = serviceSocket.getInetAddress().getHostAddress() + ":" + serviceSocket.getPort();
        System.out.println("TelnetService(" + id + "): new service working for client from " + id + "...");
        start();
    }

    @Override
    public void run() {
        System.out.println("TelnetService(" + id + "): started...");

        try (
                BufferedReader input = new BufferedReader(new InputStreamReader(serviceSocket.getInputStream()));
                PrintStream output = new PrintStream(serviceSocket.getOutputStream())
        ) {
            System.out.println("TelnetService(" + id + "): performing CHAP...");
            boolean allow = false;
            int trials = 1;
            while (!allow) {
                // CHAP 1. message
                String loginName = input.readLine();
                System.out.println("TelnetService(" + id + "): CHAP-1: received loginName=" + loginName);
                String passwordHash = server.getPasswordHashFromLoginName(loginName);

                // CHAP 2. message
                String challenge = HashUtils.generateChallenge(10);
                output.println(challenge);
                System.out.println("TelnetService(" + id + "): CHAP-2: sent challenge=" + challenge);

                // CHAP 3. message
                String responseSoll = HashUtils.hash(challenge + passwordHash);
                String responseIst = input.readLine();
                System.out.println("TelnetService(" + id + "): CHAP-3: received response=" + responseIst);

                // CHAP 4. message
                if (responseSoll.equals(responseIst)) {
                    output.println("OK");
                    allow = true;
                } else {
                    output.println("FAILED");
                    trials++;
                    if (trials > 3) {
                        return;
                    }
                    Thread.sleep(3000);         // reduce attack speed
                }
                System.out.println("TelnetService(" + id + "): CHAP-4: sent " + (allow ? "OK" : "FAILED"));
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


            serviceSocket.close();
        } catch (
                Exception e) {
            System.err.println("TelnetService(" + id + "): ERROR " + e);
        } finally {
            System.out.println("TelnetService(" + id + "): stopped.");
        }
    }
}
