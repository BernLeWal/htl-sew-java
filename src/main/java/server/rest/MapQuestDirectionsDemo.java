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

        MapQuestDirections dir = new MapQuestDirections();

        try {
            dir.query("Vienna, Austria", "Schladming, Austria");
        } catch (RESTException e) {
            e.printStackTrace();
        }
    }
}
