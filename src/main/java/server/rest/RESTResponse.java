package server.rest;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class RESTResponse {
    public final InputStream inputStream;

    public RESTResponse(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    /**
     * Reads the content of the response as String fully.
     * @return The result content of the REST-query.
     * @throws RESTException I/O-Error when reading from the inputStream.
     */
    public String readAsString() throws RESTException {
        StringBuilder content = new StringBuilder();
        try ( BufferedReader reader = new BufferedReader( new InputStreamReader(inputStream, StandardCharsets.UTF_8)) ) {
            while(reader.ready()) {
                content.append(reader.readLine()).append("\n");
            }
        } catch (IOException e) {
            throw new RESTException(500, e.getLocalizedMessage());
        }
        return content.toString();
    }

    /**
     * Reads the content of the response as JSON-tree.
     * It does not use object-mapping deserialisation.
     *
     * @return Json-Tree representation of the content.
     * @throws RESTException I/O- or JSON-Parse error when reading from the inputStream.
     */
    public JsonNode readAsJSON() throws RESTException {
        JsonFactory factory = new JsonFactory();
        ObjectMapper mapper = new ObjectMapper(factory);
        try {
            return mapper.readTree(inputStream);
        } catch (IOException e) {
            throw new RESTException(500, e.getLocalizedMessage());
        }
    }
}
