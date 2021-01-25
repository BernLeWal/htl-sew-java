package server.rest;

import java.util.Scanner;

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
