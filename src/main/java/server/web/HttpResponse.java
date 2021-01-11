package server.web;

import java.io.*;
import java.net.URL;
import java.util.HashMap;

public class HttpResponse {

    private final OutputStream outputStream;

    private int statusCode;
    private String statusMessage;
    private HashMap<String, String> headers = new HashMap<>();

    public HttpResponse(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public HashMap<String, String> getHeaders() {
        return headers;
    }

    public void sendHeaders(int statusCode, String statusMessage) {
        System.out.println("HttpResponse info:");

        // HTTP status codes:
        // https://developer.mozilla.org/de/docs/Web/HTTP/Status
        try {
            PrintStream output = new PrintStream(outputStream);

            // HTTP response status
            final String param = "HTTP/1.0 " + statusCode + " " + statusMessage;
            output.print(param);
            System.out.println("\t" + param);

            // HTTP response headers
            System.out.println("\tHeader Values:");
            for (String key : headers.keySet()) {
                final String value = headers.get(key);
                output.print(key + ": " + value + "\r\n");
                System.out.println("\t\t" + key + ": " + value);
            }

            // newline indicates that the HTTP header is finished and content will follow
            output.print("\r\n");
            output.flush();

            this.statusCode = statusCode;
            this.statusMessage = statusMessage;
        } catch (Exception e) {
            System.err.println("HttpResponse unexpected error sending HTTP response headers " + e);
        }
    }

    public void sendFile(String fileName) {
        final URL resource = getClass().getResource(fileName);
        if (resource == null) {
            System.err.println("HttpResponse error : file " + fileName + " not found!");
            sendHeaders(404, "Not Found");
            return;
        }

        try {
            File file = new File(resource.toURI());
            fileName = file.getAbsolutePath();
            int numOfBytes = (int) file.length();

            // send HTTP response header
            headers.put("Content-Type", getMimeTypeFromFileName(fileName));
            headers.put("Content-Length", String.valueOf(numOfBytes));

            // send content
            try (PrintStream out = new PrintStream(outputStream);
                 FileInputStream is = new FileInputStream(fileName)) {

                sendHeaders(200, "OK");

                // copy the file contents to the output
                byte[] buf = new byte[1024];
                int len;
                while ((len = is.read(buf)) != -1) {
                    out.write(buf, 0, len);
                }
                out.flush();
            } catch (IOException e) {
                System.err.println("HttpResponse error during sending: " + e);
                sendHeaders(500, "Internal Server Error");
            }
        } catch (Exception e) {
            System.err.println("HttpResponse unexpected error processing file '" + fileName + "': " + e);
            sendHeaders(500, "Internal Server Error");
        }

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
