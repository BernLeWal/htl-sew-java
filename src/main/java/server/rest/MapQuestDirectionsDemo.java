package server.rest;

import java.util.Scanner;

/**
 * MapQuestDirectionsDemo shows how to use REST-GET Webservice calls to the MapQuest-Directions API
 * to build a very simple road/tour-navigation system.
 * It uses the URL-class to do the HTTP transfers and the Jackson-Library to parse JSON.
 *
 * API-Documentation see https://developer.mapquest.com/documentation/directions-api
 *
 * ATTENTION: You must provide a authentication-key (stored in MAPQUEST_API_KEY) to use that service!
 * see https://developer.mapquest.com/ - Get a free API key
 */
public class MapQuestDirectionsDemo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter MapQuest Authentication Key: ");
        MapQuestDirections.MAPQUEST_API_KEY = sc.nextLine();

        MapQuestDirections route = new MapQuestDirections();

        try {
            route.query("Vienna, Austria", "Schladming, Austria");

            // output the results
            System.out.printf("API status: %d = %s\n", route.getStatuscode(), (route.getStatuscode()==0)?"Route erfolgreich ermittelt.":route.getErrorMessage());
            System.out.printf("Reisedauer: %s\n", route.getFormattedTime());
            System.out.printf("Distanz: %.2f\n", route.getDistance());

            System.out.println("============================");
            for( MapQuestDirections.Maneuver m : route.getManeuvers() ) {
                System.out.printf("%s (%.2f km)\n", m.narrative, m.distance);
            }
        } catch (RESTException e) {
            e.printStackTrace();
        }
    }
}
