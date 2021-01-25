package server.rest;

/**
 * MapQuestDirections implements the Directions-API from the MapQuest webserver.
 *
 * ATTENTION: You must provide a authentication-key (stored in MAPQUEST_API_KEY) to use that service!
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

    private Unit unit = Unit.KILOMETERS;
    private RouteType routeType = RouteType.FASTEST;
    private Locale locale = Locale.de_DE;

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

        RESTResponse rs = rq.execute();

        return true;
    }
}
