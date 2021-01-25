package server.rest;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

/**
 * RESTRequest executes a GET-Request via HTTP to a REST-Server
 */
public class RESTRequest {
    private final String resourceUrl;
    private final HashMap<String, String> queryParams = new HashMap<>();
    private final HashMap<String, String> headerParams = new HashMap<>();

    public RESTRequest(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public void setQueryParam(String key, String value) {
        if( value==null ) {
            // delete the query param
            queryParams.remove(key);
        } else {
            queryParams.put(key, value);
        }
    }

    public String getQueryParam(String key) {
        return queryParams.get(key);
    }

    public void setHeaderParam(String key, String value) {
        if( value==null ) {
            // delete the query param
            headerParams.remove(key);
        } else {
            headerParams.put(key, value);
        }
    }

    public String getHeaderParam(String key) {
        return headerParams.get(key);
    }

    public RESTResponse get() throws RESTException {
        StringBuilder rawUrl = new StringBuilder();
        rawUrl.append(resourceUrl);

        boolean isFirst = true;
        for(String key : queryParams.keySet() ) {
            String value = queryParams.get( key );

            if( isFirst ) {
                rawUrl.append("?");
                isFirst = false;
            }
            else
                rawUrl.append("&");
            rawUrl.append(URLEncoder.encode(key, StandardCharsets.UTF_8)).append("=").append(URLEncoder.encode(value, StandardCharsets.UTF_8));
        }
        System.out.println("RESTRequest execute: GET " + rawUrl.toString());

        try {
            URL url = new URL(rawUrl.toString());
            return new RESTResponse(url.openConnection().getInputStream());
        } catch (IOException e) {
            throw new RESTException(500, e.getLocalizedMessage());
        }
    }
}
