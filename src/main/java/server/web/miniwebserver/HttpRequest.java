package server.web.miniwebserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpRequest {
    private String method;
    private String path = "";

    public HttpRequest(InputStream inputStream) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        boolean isFirstLine = true;
        while ((line = input.readLine()) != null) {
            if (line.isEmpty())
                break;
            //System.out.println("MyService T" + thread_nr + ": received '" + line + "'");

            if (isFirstLine) {
                // evaluate HTTP method
                if (isParam(line, "GET ")) {
                    method = "GET";
                } else if (isParam(line, "POST ")) {
                    method = "POST";
                }
                // evaluate requested path to the ressource
                path = getValue(line, method + " ");
                path = path.substring(0, path.indexOf(' '));

                System.out.println("HttpRequest: " + method + " " + path);
                isFirstLine = false;
            } else {
                // evaluate the header here...
            }
        }

        if (path.isEmpty())
            path = "/";
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }


    private static boolean isParam(String line, String name) {
        if (name == null || name.isEmpty())
            return false;
        if (line == null || line.isEmpty())
            return false;
        if (line.length() < name.length())
            return false;
        return line.substring(0, name.length()).equals(name);
    }

    private static String getValue(String line, String name) {
        if (name == null || name.isEmpty())
            return null;
        if (line == null || line.isEmpty())
            return null;
        return line.substring(name.length());
    }
}
