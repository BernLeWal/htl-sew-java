package server.rest.opendata;

import patterns.parser.CsvParser;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * OpenDataDemo accesses the following catalog of "Open Data Österreich" (https://www.data.gv.at/):
 * "Katalog Gestorbene in Österreich (ohne Auslandssterbefälle) ab 2000 nach Kalenderwoche"
 * (https://www.data.gv.at/katalog/dataset/d3b85461-fc0d-3639-9aa9-39211c4ecade)
 * <p>
 * It calculates the total amount of deaths per year
 * <p>
 * Attention: when retrieving the URL from the web-site, always change the protocol to "https:", because
 * the server will return only empty files when "http:" is used!
 */
public class OpenDataDemo {
    // ATTENTION: downloading from open-data only with HTTPS!
    public static final String DOWNLOAD_URL = "https://data.statistik.gv.at/data/OGD_gest_kalwo_GEST_KALWOCHE_100.csv";

    public static void main(String[] args) {
        try {
            // STEP 1: Download the CSV file
            var conn = new URL(DOWNLOAD_URL).openConnection();
            var input = conn.getInputStream();
            var table = new CsvParser().parse(input);
            System.out.printf("CSV file contains %d rows.\n", table.size());
            // Dump out the read data:
//            for( ArrayList<String> columns : table ) {
//                System.out.println( columns );
//            }
            // or (more simple)
//            table.forEach(System.out::println);

            // STEP 2: convert plain strings into numbers (to be able to evaluate information)
            ArrayList<ArrayList<Integer>> dataTable = new ArrayList<>();
            for (ArrayList<String> row : table) {
                ArrayList<Integer> dataRow = new ArrayList<>();

                String column = row.get(0).substring(5);                  // "KALW-200445" --> "200445"
                // column 0: year
                dataRow.add(Integer.valueOf(column.substring(0, 4)));     // "200445" --> "2004" --> 2004
                // column 1: week
                dataRow.add(Integer.valueOf(column.substring(4)));        // "200445" --> "45" --> 45
                // column 2: "Bundesland" as number
                dataRow.add(Integer.valueOf(row.get(1).substring(4)));    // "B00-8" --> "8" --> 8
                // column 3: age-group (1..<65; 2..>=65)
                dataRow.add(Integer.valueOf(row.get(2).substring(11)));   // "ALTERSGR65-1" --> "1" --> 1
                // column 4: sex (1..male/2..female)
                dataRow.add(Integer.valueOf(row.get(3).substring(4)));    // "C11-2" --> "2" --> 2
                // column 5: number of deaths
                dataRow.add(Integer.valueOf(row.get(4)));                 // "124" --> 124

                dataTable.add(dataRow);
            }
            System.out.printf("Datatable contains %d rows.\n", dataTable.size());
            // Dump out the converted raw data:
//            dataTable.forEach(System.out::println);
            System.out.println();

            // STEP 3: calculate total deaths per year
            System.out.println("Total deaths per year:");
            TreeMap<Integer, Integer> totalDeathsPerYear = new TreeMap<>();
            for (ArrayList<Integer> dataRow : dataTable) {
                //eventually implement a filter here,
                //f.e. ignore all dataRows which are other then younger han 65
                if (dataRow.get(3) != 1 /* younger than 65 */)
                    continue;

                int year = dataRow.get(0);
                Integer currentDeaths = totalDeathsPerYear.get(year);
                totalDeathsPerYear.put(year, ((currentDeaths != null) ? currentDeaths : 0) + dataRow.get(5));
            }
            totalDeathsPerYear.keySet().forEach((key) -> System.out.println(key + ": " + totalDeathsPerYear.get(key)));


        } catch (IOException e) {
            System.err.println("Failed to read from " + DOWNLOAD_URL + "!");
            e.printStackTrace();
        }
    }
}
