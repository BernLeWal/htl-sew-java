package server.rest;

public class RESTException extends Exception {
    private final int statusCode;

    public RESTException(int statusCode, String statusMessage) {
        super(statusCode + ": " + statusMessage);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
