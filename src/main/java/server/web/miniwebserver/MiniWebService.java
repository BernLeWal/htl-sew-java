package server.web.miniwebserver;

import java.io.IOException;
import java.net.Socket;

public class MiniWebService extends Thread {
    private static int thread_count = 0;

    private final Socket session;
    private final int thread_nr;

    public MiniWebService(Socket serviceSocket) {
        this.thread_nr = ++thread_count;
        this.session = serviceSocket;

        System.out.println("MiniWebService T" + thread_nr + ": new service working for client from "
                + serviceSocket.getInetAddress().getHostAddress() + ":" + serviceSocket.getPort());
    }

    @Override
    public void run() {
        System.out.println("MiniWebService T" + thread_nr + ": waiting for input from the client...");
        try {
            // read request
            HttpRequest request = new HttpRequest(session.getInputStream());
            HttpResponse response = new HttpResponse(session.getOutputStream());
            response.process(request.getPath());

            session.getOutputStream().close();
            session.close();
        } catch (IOException e) {
            System.err.println("MiniWebService T" + thread_nr + " stopped with error " + e);
        }
        System.out.println("MiniWebService T" + thread_nr + " stopped.");
    }
}
