package server.web.miniwebserver;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class HttpResponse {
    private OutputStream outputStream;

    public HttpResponse(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void process(String subPage) throws IOException {
        // String html = "<HTML><BODY>" + fileName + "</BODY></HTML>";

        if (subPage.isEmpty() || subPage.equalsIgnoreCase("/")) {
            subPage = "/index.html";
        }

        String fileName = "/server/sockets/www" + subPage;
        try {
            // MIME-TYPES:
            // https://developer.mozilla.org/en-US/docs/Web/HTTP/Basics_of_HTTP/MIME_types/Complete_list_of_MIME_types
            if (fileName.endsWith(".html") || fileName.endsWith(".htm"))
                sendText(fileName, "text/html");
            else if (fileName.endsWith(".css"))
                sendText(fileName, "text/css");
            else if (fileName.endsWith(".js"))
                sendText(fileName, "text/javascript");
            else if (fileName.endsWith(".svg"))
                sendText(fileName, "text/svg+xml");
            else if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg"))
                sendImage(fileName, "image/jpeg");
            else if (fileName.endsWith(".gif"))
                sendImage(fileName, "image/gif");
            else if (fileName.endsWith(".png"))
                sendImage(fileName, "image/png");
            else
                sendText(fileName, "text/plain");
        } catch (Exception e) {
            System.err.println("HttpResponse stopped with error " + e);
        }

    }

    private void sendText(String fileName, String contentType) {
        // read text from file
        StringBuilder html = new StringBuilder();
        try {
            InputStream inputStream = getClass().getResourceAsStream(fileName);
            if (inputStream != null) {
                BufferedReader fileInput = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = fileInput.readLine()) != null) {
                    html.append(line);
                    html.append("\r\n");
                }
            } else {
                System.err.println("HttpResponse: failed to open " + fileName);
            }
        } catch (IOException e) {
            System.err.println("HttpResponse error when reading file '" + fileName + "'" + e);
        }

        // send text via HTTP
        try {
            BufferedWriter output = new BufferedWriter(
                    new OutputStreamWriter(new BufferedOutputStream(outputStream), StandardCharsets.UTF_8));
            sendHeader(output, "HTTP/1.1 200 OK");
            sendHeader(output, "Content-Type: " + contentType);
            sendHeader(output, "Content-Length: " + html.length());
            sendHeader(output, "\r\n");

            output.write(html.toString());
            // System.out.println("ModuleStatic: sent '" + html + "'");

            output.flush();
        } catch (Exception e) {
            System.err.println("HttpResponse error during sending: " + e);
        }
    }

    private void sendImage(String fileName, String contentType) {
        int numOfBytes = 256;
        byte[] fileInBytes = null;
        try {
            fileName = new File(getClass().getResource(fileName).toURI()).getAbsolutePath();
            // URL in lokalen Dateinamen konvertieren
            String fsep = System.getProperty("file.separator", "/");
            StringBuffer sb = new StringBuffer(fileName.length());
            for (int i = 0; i < fileName.length(); ++i) {
                char c = fileName.charAt(i);
                if (c == '/') {
                    sb.append(fsep);
                } else {
                    sb.append(c);
                }
            }
            fileName = sb.toString();
            File file = new File(fileName);
            numOfBytes = (int) file.length();

            try {
                PrintStream out = new PrintStream(outputStream);
                FileInputStream is = new FileInputStream(fileName);
                fileInBytes = new byte[numOfBytes];
                // HTTP-Header senden
                sendHeader(out, "HTTP/1.0 200 OK");
                sendHeader(out, "Server: MiniWebServer 0.5");
                sendHeader(out, "Content-type: " + contentType + "\r\n");
                // Dateiinhalt senden
                byte[] buf = new byte[256];
                int len;
                while ((len = is.read(buf)) != -1) {
                    out.write(buf, 0, len);
                }
                is.close();

                out.flush();
            } catch (IOException e) {
                System.err.println("HttpResponse error during sending: " + e);
            }

        } catch (Exception e) {
            System.err.println("HttpResponse error reading file '" + fileName + "': " + e);
        }

    }

    private void sendHeader(BufferedWriter output, String param) throws IOException {
        output.write(param + "\r\n");
        System.out.println("HttpResponse: sent '" + param + "'");
    }

    private void sendHeader(PrintStream output, String param) throws IOException {
        output.print(param + "\r\n");
        System.out.println("HttpResponse: sent '" + param + "'");
    }

}
