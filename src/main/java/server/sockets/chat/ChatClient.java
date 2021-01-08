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
        System.out.println("Client startet");
        try (
            Socket socket = new Socket("localhost", 23);
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintStream output = new PrintStream(socket.getOutputStream());
        )
        {
            Scanner scanner = new Scanner(System.in);
            System.out.println("ChatClient runs on " + socket.getInetAddress().getHostAddress()
                    + ":" + socket.getLocalPort() + " - type 'quit' to stop.");

            while (true) {
                while (input.ready()) {
                    String line = input.readLine();
                    System.out.println("ChatClient: recieved " + line);
                }

                if (System.in.available()>0 ) {
                    String line = scanner.nextLine();
                    output.println(line);
                    if (line.equalsIgnoreCase("quit")) {
                        break;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("ChatClient: ERROR " + e);
        }
    }
}
