package server.rest;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;

/**
 * MapQuestDirections implements the Directions-API from the MapQuest webserver.
 * API-Documentation see https://developer.mapquest.com/documentation/directions-api
 *
 * ATTENTION: You must provide a authentication-key (stored in MAPQUEST_API_KEY) to use that service!
 * see https://developer.mapquest.com/ - Get a free API key
 */
public class MapQuestDirections {
    public static String MAPQUEST_API_KEY = null;

    public static final String MAPQUEST_RESOURCE_URL = "http://www.mapquestapi.com/directions/v2/route";

    public enum Unit {
        MILES("m"),
        KILOMETERS("k");

        public final String value;

        Unit(String value) {
            this.value = value;
        }
    }

    public enum RouteType {
        FASTEST,
        SHORTEST,
        PEDESTRIAN,
        BICYCLE;

        public final String value;

        RouteType() {
            this.value = toString().toLowerCase(java.util.Locale.ROOT);
        }
    }

    public enum Locale {
        en_US, // English (US)
        en_GB, // English (Great Britain)
        fr_CA, // French (Canada)
        fr_FR, // French (France)
        de_DE, // German (Germany)
        es_ES, // Spanish (Spain)
        es_MX, // Spanish (Mexico)
        ru_RU; // Russian (Russia)

        public final String value;

        Locale() {
            this.value = toString();
        }
    }

    public class Maneuver {
        public String narrative;
        public double distance;
        public String mapUrl;
    }

    // inputs to the REST-call
    private Unit unit = Unit.KILOMETERS;
    private RouteType routeType = RouteType.FASTEST;
    private Locale locale = Locale.de_DE;

    // outputs of the REST-call
    private int statuscode;
    private String formattedTime;
    private double distance;
    private ArrayList<Maneuver> maneuvers = new ArrayList<>();

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public RouteType getRouteType() {
        return routeType;
    }

    public void setRouteType(RouteType routeType) {
        this.routeType = routeType;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public int getStatuscode() {
        return statuscode;
    }

    public String getFormattedTime() {
        return formattedTime;
    }

    public double getDistance() {
        return distance;
    }

    //
    // Operations:
    //
    public boolean query(String from, String to) throws RESTException {
        if( MAPQUEST_API_KEY==null || MAPQUEST_API_KEY.isEmpty() ) {
            throw new RESTException(500, "No authorization key provided!");
        }

        RESTRequest rq = new RESTRequest(MAPQUEST_RESOURCE_URL);
        rq.setQueryParam("key", MAPQUEST_API_KEY);

        rq.setQueryParam("from", from);
        rq.setQueryParam("to", to);

        rq.setQueryParam("unit", unit.value);
        rq.setQueryParam("routeType", routeType.value);
        rq.setQueryParam("locale", locale.value);

        RESTResponse rs = rq.get();

        // parse JSON-tree
        JsonNode json = rs.readAsJSON();

        this.statuscode = json.get("info").get("statuscode").asInt();
        this.formattedTime = json.get("route").get("formattedTime").asText();
        this.distance = json.get("route").get("distance").asDouble();

        var ms = json.get("route").get("legs").get(0).get("maneuvers");
        for( JsonNode child : ms ) {
            Maneuver maneuver = new Maneuver();
            maneuvers.add(maneuver);
            maneuver.narrative = child.get("narrative").asText();
            maneuver.distance = child.get("distance").asDouble();
            if( child.has("mapUrl") )
                maneuver.mapUrl = child.get("mapUrl").asText();
        }

        // output the results
        System.out.printf("API status: %d = Route erfolgreich ermittelt.\n", statuscode);
        System.out.printf("Reisedauer: %s\n", formattedTime);
        System.out.printf("Distanz: %.2f\n", distance);

        System.out.println("============================");
        for( Maneuver m : maneuvers ) {
            System.out.printf("%s (%.2f km)\n", m.narrative, m.distance);
        }

        return true;
    }
}
