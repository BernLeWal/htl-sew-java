package core.streams.opendata;

import patterns.parser.CsvParser;

import java.io.IOException;
import java.net.URL;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * AustriaDeathsPerDistrictSince2020 accesses the following catalog of "Open Data Österreich" (https://www.data.gv.at/):
 * Katalog Altersstandardisierte Sterberate in Österreich (ohne Auslandssterbefälle) ab 2020 nach Kalenderwoche, Bundesland und Bezirken
 * (https://www.data.gv.at/katalog/dataset/14589f68-d1ac-3be8-ad3d-07249865fb85)
 *
 * Here filter district Schwaz/Tyrol, the district in Austria where a vaccination-study with the Biontech/Pficer vaxine.
 * did take place in March 2021.
 * Until 2021-03-19 65% of the inhabitats got their first vaxine.
 */
public class AustriaDeathsPerDistrictSince2020 {
    public static void main(String[] args) throws IOException {
        var rawData = new CsvParser()
                // STEP 1: Download the CSV file
                .parse(new URL("https://data.statistik.gv.at/data/OGD_rate_kalwobez_GEST_KALWOCHE_STR_BZ_100.csv").openStream() )
                .stream()
                // STEP 2: convert plain strings into numbers (to be able to evaluate information)
                .map(row -> new RawData(
                        row.get(0).substring(5),
                        Integer.parseInt(row.get(1).substring(9)),
                        Integer.parseInt(row.get(2).substring(6)),
                        (int) Double.parseDouble(row.get(3).replace(',', '.')),
                        Double.parseDouble(row.get(4).replace(',', '.'))))
                // STEP 3: filter data
                .filter(record -> (record.districtId == 709))   // "POLBEZKW-709;Schwaz;;;;;;;;" --> Id 709
                .collect(Collectors.toList());

        // STEP 4: calculate/aggregate data
        // Here calculate total deaths per week
        TreeMap<String, TreeMap<String, Integer>> totalDeathsPerYearAndWeek = new TreeMap<>();
        //TreeMap<String, Integer> totalDeathsPerWeek = new TreeMap<>();
        for (var record : rawData) {
            String year = record.yearWeek.substring(0, 4);
            var currentYear = totalDeathsPerYearAndWeek.computeIfAbsent(year, k -> new TreeMap<>());

            var week = record.yearWeek.substring(4);
            Integer currentDeaths = currentYear.get(week);
            currentYear.put(week, ((currentDeaths != null) ? currentDeaths : 0) + record.amount);
        }

        // STEP 5: output the aggregated data
        //totalDeathsPerWeek.keySet().forEach((key) -> System.out.println(key + ": " + totalDeathsPerWeek.get(key)));
        String firstYear = null;
        System.out.print("Week");
        for (var year : totalDeathsPerYearAndWeek.keySet()) {
            if (firstYear == null)
                firstYear = year;
            System.out.printf("\t%s", year);
        }
        System.out.println();
        for (var week : totalDeathsPerYearAndWeek.get(firstYear).keySet()) {
            System.out.printf("%s. \t", week);
            for (var year : totalDeathsPerYearAndWeek.keySet()) {
                System.out.printf("%d\t\t", totalDeathsPerYearAndWeek.get(year).get(week));
            }
            System.out.println();
        }

    }

    public static record RawData(
            String yearWeek,
            int districtId,
            int sexId,
            int amount,
            double rate
    ) {
    }
}
