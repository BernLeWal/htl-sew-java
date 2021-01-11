package server.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class HttpRequest {
    private String method = "";
    private String path = "";
    private final HashMap<String, String> headers = new HashMap<>();
    private final StringBuilder content = new StringBuilder();

    public HttpRequest(InputStream inputStream) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(inputStream));

        System.out.println("HttpRequest info:");

        // first line contains HTTP method path and protocol-version
        String line = input.readLine();
        if( line!=null && !line.isEmpty() ) {
            String[] parts = line.split(" ");
            if( parts.length>0 )
                method = parts[0];
            if( parts.length>1 )
                path = parts[1];
            // skip parts[2], the HTTP protocol

            if (method.isEmpty())
                method = "GET";
            if (path.isEmpty())
                path = "/";
            System.out.println("\t" + method + " " + path + " " + ((parts.length>2) ? parts[2] : "") );
        }

        // further lines contain the HTTP header parameters
        System.out.println("\tHeader values:");
        while ((line = input.readLine()) != null) {
            String[] parts = line.split(": ");
            if( parts.length > 1 ) {
                headers.put(parts[0], parts[1]);
                System.out.println("\t\t" + parts[0] + ": " + parts[1]);
            }

            if (line.isEmpty())
                break;  // empty line means that now the content starts.
        }

        // after the empty line follows the content if provided
        if( line!=null && input.ready()) {
            while ((line = input.readLine()) != null) {
                content.append(line);
                content.append("\n");
            }
            System.out.println();
            System.out.println(content);
        }
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public HashMap<String, String> getHeaders() {
        return headers;
    }

    public String getContent() {
        return content.toString();
    }
}
