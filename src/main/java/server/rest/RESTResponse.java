package server.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RESTResponse {
    public RESTResponse(InputStream inputStream) throws RESTException {
        try ( BufferedReader reader = new BufferedReader( new InputStreamReader(inputStream)) ) {
            while(reader.ready()) {
                System.out.println(reader.readLine());
            }
        } catch (IOException e) {
            throw new RESTException(500, e.getLocalizedMessage());
        }
    }
}
