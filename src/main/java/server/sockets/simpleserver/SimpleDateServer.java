/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.sockets.simpleserver;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * SimpleDateServer is a TCP server that runs on port 9090.
 * When a client connects, it sends the client the current date and time,
 * then closes the connection with that client.
 * <p>
 * Arguably just about the simplest server you can write.
 * <p>
 * http://cs.lmu.edu/~ray/notes/javanetexamples/
 */
public class SimpleDateServer {

    /**
     * Runs the server.
     */
    public static void main(String[] args) throws IOException {
        try (ServerSocket listener = new ServerSocket(9090)) {
            while (true) {
                try (Socket socket = listener.accept()) {
                    PrintWriter out =
                            new PrintWriter(socket.getOutputStream(), true);
                    out.println(new Date().toString());
                }
            }
        }
    }
}