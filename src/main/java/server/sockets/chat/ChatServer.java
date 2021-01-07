/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.sockets.chat;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * ChatServer is the server of the ChatServer-Demo
 * The server listens for incoming connections from new clients and
 * delegates the newly created socket for a client to an extra ChatService thread to be answered.
 * <p>
 * Run an instance of this before starting any clients.
 */
public class ChatServer {
    private ServerSocket listener;
    private ArrayList<ChatService> clients = new ArrayList<>();

    public static void main(String[] args) {
        new ChatServer().run();
    }

    public void run() {
        try {
            listener = new ServerSocket(23);
            try {
                while (true) {
                    System.out.println("ChatServer is running, waiting for incoming on "
                            + InetAddress.getLocalHost().getHostAddress()
                            + ":" + listener.getLocalPort());
                    Socket serviceSocket = listener.accept();
                    ChatService service = new ChatService(this, serviceSocket);
                    clients.add(service);
                    service.start();
                }
            } finally {
                listener.close();
            }
        } catch (IOException e) {
            System.err.println("ChatServer: ERROR " + e);
        }
    }

    public synchronized void postMessage(String msg) {
        for (ChatService client : clients) {
            client.postMessage(msg);
        }
    }
}
