/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.sockets.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * ChatClient is a console-based client for the ChatServer in this demo
 */
public class ChatClient {

    public static void main(String[] args) {
        Socket clientSocket;
        try {
            clientSocket = new Socket("localhost", 23);
            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintStream output = new PrintStream(clientSocket.getOutputStream());

            Scanner scanner = new Scanner(System.in);
            System.out.println("ChatClient runs on " + clientSocket.getInetAddress().getHostAddress()
                    + ":" + clientSocket.getLocalPort() + " - type 'quit' to stop.");
            while (true) {
                String line;

                while (input.ready()) {
                    line = input.readLine();
                    System.out.println("ChatClient: recieved " + line);
                }

                line = scanner.nextLine();
                output.println(line);
                if (line.equalsIgnoreCase("quit")) {
                    break;
                }

            }

            output.close();
            input.close();
            clientSocket.close();
        } catch (IOException e) {
            System.err.println("ChatClient: ERROR " + e);
        }
    }
}