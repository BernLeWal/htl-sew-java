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

/**
 * ChatService is the part of the ChatServer, which responds to the requests from one specific client.
 */
public class ChatService extends Thread {

    private final ChatServer server;
    private final Socket serviceSocket;

    private BufferedReader input = null;
    private PrintStream output = null;

    public ChatService(ChatServer server, Socket serviceSocket) {
        this.server = server;
        this.serviceSocket = serviceSocket;
        System.out.println("ChatService: new service working for client from "
                + serviceSocket.getInetAddress().getHostAddress()
                + ":" + serviceSocket.getPort());
    }

    @Override
    public void run() {
        System.out.println("ChatService: waiting for input from the client...");
        try {
            input = new BufferedReader(new InputStreamReader(serviceSocket.getInputStream()));
            output = new PrintStream(serviceSocket.getOutputStream());
            try {
                while (true) {
                    String line = input.readLine();
                    if (line == null || line.equalsIgnoreCase("quit")) {
                        break;
                    }
                    System.out.println("ChatService: echo " + line);
                    server.postMessage(line);
                }
            } finally {
                output.close();
                output = null;
                input.close();
                input = null;
            }
            serviceSocket.close();
        } catch (IOException ex) {
            System.err.println("ChatService: ERROR " + ex);
        }
        System.out.println("ChatService: stopped.");
    }

    public synchronized void postMessage(String msg) {
        if (output != null) {
            output.println(msg);
        }
    }
}
