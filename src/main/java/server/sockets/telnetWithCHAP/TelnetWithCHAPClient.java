package server.sockets.telnetWithCHAP;

import utils.HashUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * TelnetWithCHAPClient is a console-based client for the TelnetWithChapServer in this samples
 */
public class TelnetWithCHAPClient {

    public static void main(String[] args) throws Exception {
        // Read login and password from the user
        BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));

        try (
            Socket clientSocket = new Socket("127.0.0.1", 23);
            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintStream output = new PrintStream(clientSocket.getOutputStream())) {

            // CHAP protocol implementation...
            boolean allow = false;
            while (!allow) {
                System.out.print("Login:");
                String loginName = scanner.readLine();
                System.out.print("Password:");
                String password = scanner.readLine();
                String passwordHash = HashUtils.hash(password);
                System.out.println("TelnetClient: loginName=" + loginName + ", passwordHash=" + passwordHash);

                // CHAP 1. message
                output.println(loginName);
                System.out.println("TelnetClient: CHAP-1: sent loginName=" + loginName);

                // CHAP 2. message
                String challenge = input.readLine();
                System.out.println("TelnetClient: CHAP-2: received challenge=" + challenge);

                // CHAP 3. message
                String response = HashUtils.hash(challenge + passwordHash);
                output.println(response);
                System.out.println("TelnetClient: CHAP-3: sent response=" + response);

                // CHAP 4. message
                String result = input.readLine();
                System.out.println("TelnetClient: CHAP-4: received " + result);
                if (result.equals("OK"))
                    allow = true;
            }
            // Login OK, perform telnet services
            while (allow) {
                if (input.ready()) {
                    System.out.print((char) input.read());
                }

                if (scanner.ready()) {
                    output.print((char) scanner.read());
                }
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }

}
