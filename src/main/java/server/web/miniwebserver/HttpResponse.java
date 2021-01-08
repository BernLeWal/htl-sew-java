package server.web.miniwebserver;

import java.io.*;
import java.net.URL;

public class HttpResponse {
    public static final String WWW_FILES_BASE_PATH = "/server/sockets/www";

    private final OutputStream outputStream;

    private int statusCode;
    private String statusMessage;

    public HttpResponse(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void process(String subPage) throws IOException {
        if (subPage.isEmpty() || subPage.equalsIgnoreCase("/")) {
            subPage = "/index.html";
        }

        sendFile(WWW_FILES_BASE_PATH + subPage, getMimeTypeFromFileName(subPage));
    }

    private void sendFile(String fileName, String contentType) {
        final URL resource = getClass().getResource(fileName);
        try (PrintStream out = new PrintStream(outputStream) ) {
            if (resource == null) {
                System.err.println("HttpResponse error : file " + fileName + " not found!");
                sendStatus(out, 404, "Not Found");
                sendNewLine(out);
                return;
            }

            File file = new File(resource.toURI());
            fileName = file.getAbsolutePath();
            int numOfBytes = (int) file.length();

            try (FileInputStream is = new FileInputStream(fileName)) {
                // HTTP-Header senden
                sendStatus(out, 200, "OK");
                sendHeader(out, "Server", "MiniWebServer 0.5");
                sendHeader(out, "Content-Type", contentType);
                sendHeader(out, "Content-Length", String.valueOf(numOfBytes));
                sendNewLine(out);
                // Dateiinhalt senden
                byte[] buf = new byte[1024];
                int len;
                while ((len = is.read(buf)) != -1) {
                    out.write(buf, 0, len);
                }
            } catch (IOException e) {
                System.err.println("HttpResponse error during sending: " + e);
                sendStatus(out, 500, "Internal Server Error");
                sendNewLine(out);
            } finally {
                out.flush();
            }
        } catch (Exception e) {
            System.err.println("HttpResponse unexpected error processing file '" + fileName + "': " + e);
        }

    }

    private void sendStatus(PrintStream output, int code, String message) {
        // HTTP status codes:
        // https://developer.mozilla.org/de/docs/Web/HTTP/Status
        final String param = "HTTP/1.0 " + code + " " + message;
        output.print(param + "\r\n");
        System.out.println("HttpResponse: sent '" + param + "'");
        this.statusCode = code;
        this.statusMessage = message;
    }

    private void sendHeader(PrintStream output, String key, String value) {
        final String param = key + ": " + value;
        output.print(param + "\r\n");
        System.out.println("HttpResponse: sent '" + param + "'");
    }

    private void sendNewLine(PrintStream output) {
        output.print("\r\n");
    }

    private static String getMimeTypeFromFileName(String fileName) {
        // MIME-TYPES:
        // https://developer.mozilla.org/en-US/docs/Web/HTTP/Basics_of_HTTP/MIME_types/Complete_list_of_MIME_types
        if (fileName.endsWith(".html") || fileName.endsWith(".htm"))
            return "text/html";
        else if (fileName.endsWith(".css"))
            return "text/css";
        else if (fileName.endsWith(".js"))
            return "text/javascript";
        else if (fileName.endsWith(".svg"))
            return "text/svg+xml";
        else if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg"))
            return "image/jpeg";
        else if (fileName.endsWith(".gif"))
            return "image/gif";
        else if (fileName.endsWith(".png"))
            return "image/png";
        else
            return "text/plain";
    }

}
