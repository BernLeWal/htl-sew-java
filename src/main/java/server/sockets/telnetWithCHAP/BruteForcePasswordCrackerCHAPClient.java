package server.sockets.telnetWithCHAP;

import utils.HashUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * BruteForcePasswordCrackerCHAPClient demonstrates a brute-force password attack
 * implementing CHAP (challenge-authentication-protocol).
 */
public class BruteForcePasswordCrackerCHAPClient {

    public static final String PASSWORD_CHARS = "0123456789";

    public static void main(String[] args) throws Exception {
        String loginName = "admin";
        System.out.println("Searching password for user " + loginName);

        try (
            Socket clientSocket = new Socket("127.0.0.1", 23);
            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintStream output = new PrintStream(clientSocket.getOutputStream()))
        {
            String password;
            int passwordLength = 1;  // minimum lenght of the password
            while (true) {
                int[] chars = new int[passwordLength];
                boolean allPossibilitiesDone = false;
                while (!allPossibilitiesDone) {
                    password = "";
                    for (int pwdChar = 0; pwdChar < passwordLength; pwdChar++) {
                        password += PASSWORD_CHARS.charAt(chars[pwdChar]);
                    }
                    System.out.println("trying " + password);
                    String passwordHash = HashUtils.hash(password);

                    // CHAP protocol implementation...
                    output.println(loginName);              // CHAP 1. message
                    String challenge = input.readLine();    // CHAP 2. message
                    output.println(HashUtils.hash(challenge + passwordHash));    // CHAP 3. message
                    String result = input.readLine();       // CHAP 4. message

                    if (result.equals("OK")) {
                        System.out.println("User " + loginName + " has password " + password);
                        return;
                    } else {
                        for (int pwdChar = passwordLength - 1; pwdChar >= 0; pwdChar--) {
                            chars[pwdChar]++;
                            if (chars[pwdChar] >= PASSWORD_CHARS.length()) {
                                chars[pwdChar] = 0;
                                if (pwdChar == 0) {
                                    allPossibilitiesDone = true;
                                }
                            } else {
                                break;
                            }
                        }
                    }
                }
                passwordLength++;
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }

}
