package server.rest.opendata;

import patterns.parser.CsvParser;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * NicerOpenDataDemo accesses the following catalog of "Open Data Österreich" (https://www.data.gv.at/):
 * "Katalog Gestorbene in Österreich (ohne Auslandssterbefälle) ab 2000 nach Kalenderwoche"
 * (https://www.data.gv.at/katalog/dataset/d3b85461-fc0d-3639-9aa9-39211c4ecade)
 * Improvement: It stores the data in specific objects instead of unspecific tables of strings.
 * <p>
 * It calculates the total amount of deaths per year, per agegroup and per male/female
 * <p>
 * Attention: when retrieving the URL from the web-site, always change the protocol to "https:", because
 * the server will return only empty files when "http:" is used!
 */
public class NicerOpenDataDemo {
    // ATTENTION: downloading from open-data only with HTTPS!
    public static final String DOWNLOAD_URL = "https://data.statistik.gv.at/data/OGD_gest_kalwo_GEST_KALWOCHE_100.csv";

    public static void main(String[] args) {
        try {
            // STEP 1: Download the CSV file
            var conn = new URL(DOWNLOAD_URL).openConnection();
            var input = conn.getInputStream();
            var table = new CsvParser().parse(input);
            System.out.printf("CSV file contains %d rows.\n", table.size());

            // STEP 2: convert plain strings into numbers (to be able to evaluate information)
            ArrayList<DeathsPerWeek> dataTable = new ArrayList<>();
            for (ArrayList<String> row : table) {
                DeathsPerWeek dataRow = new DeathsPerWeek();

                String column = row.get(0).substring(5);                             // "KALW-200445" --> "200445"
                dataRow.setYear(Integer.parseInt(column.substring(0, 4)));           // "200445" --> "2004" --> 2004
                dataRow.setWeekNr(Integer.parseInt(column.substring(4)));            // "200445" --> "45" --> 45
                dataRow.setProvince(Integer.parseInt(row.get(1).substring(4)));      // "B00-8" --> "8" --> 8
                dataRow.setAgeGroup(Integer.parseInt(row.get(2).substring(11)));     // "ALTERSGR65-1" --> "1" --> 1
                dataRow.setSex(Integer.parseInt(row.get(3).substring(4)));           // "C11-2" --> "2" --> 2
                dataRow.setDeaths(Integer.parseInt(row.get(4)));                     // "124" --> 124

                dataTable.add(dataRow);
            }
            System.out.printf("Datatable contains %d rows.\n", dataTable.size());
            // Dump out the converted raw data:
//            dataTable.forEach(System.out::println);
            System.out.println();

            // STEP 3: calculate total deaths per year
            System.out.println("Total deaths per year:");
            TreeMap<Integer, CalculatedDeathsPerYear> totalDeathsPerYear = new TreeMap<>();
            for (DeathsPerWeek dataRow : dataTable) {
                int year = dataRow.getYear();
                int deaths = dataRow.getDeaths();

                CalculatedDeathsPerYear current = totalDeathsPerYear.get(year);
                if( current == null ) {
                    current = new CalculatedDeathsPerYear();
                    totalDeathsPerYear.put(year, current);
                }

                current.incTotalDeaths( deaths );
                if( dataRow.getAgeGroup()==1 /* younger than 65*/ )
                    current.incDeathsYounger65(deaths);
                else
                    current.incDeathsOlder65(deaths);
                if( dataRow.getSex()==1 /* male */)
                    current.incDeathsMale(deaths);
                else
                    current.incDeathsFemale(deaths);
            }
            totalDeathsPerYear.keySet().forEach((key) -> System.out.println(key + ": " + totalDeathsPerYear.get(key)));


        } catch (IOException e) {
            System.err.println("Failed to read from " + DOWNLOAD_URL + "!");
            e.printStackTrace();
        }
    }

    public static class CalculatedDeathsPerYear {
        private int totalDeaths;
        private int deathsYonger65;
        private int deathsOlder65;
        private int deathsMale;
        private int deathsFemale;

        public int getTotalDeaths() {
            return totalDeaths;
        }

        public int getDeathsYonger65() {
            return deathsYonger65;
        }

        public int getDeathsOlder65() {
            return deathsOlder65;
        }

        public int getDeathsMale() {
            return deathsMale;
        }

        public int getDeathsFemale() {
            return deathsFemale;
        }

        public void incTotalDeaths(int value) {
            totalDeaths += value;
        }

        public void incDeathsYounger65(int value) {
            deathsYonger65 += value;
        }

        public void incDeathsOlder65(int value) {
            deathsOlder65 += value;
        }

        public void incDeathsMale(int value) {
            deathsMale += value;
        }

        public void incDeathsFemale(int value) {
            deathsFemale += value;
        }

        @Override
        public String toString() {
            return "{" +
                    "total=" + totalDeaths +
                    ", yonger65=" + deathsYonger65 +
                    ", older65=" + deathsOlder65 +
                    ", male=" + deathsMale +
                    ", female=" + deathsFemale +
                    '}';
        }
    }

    public static class DeathsPerWeek {
        private int year;
        private int weekNr;
        private int province;
        private int ageGroup;
        private int sex;

        private int deaths;

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public int getWeekNr() {
            return weekNr;
        }

        public void setWeekNr(int weekNr) {
            this.weekNr = weekNr;
        }

        public int getProvince() {
            return province;
        }

        public void setProvince(int province) {
            this.province = province;
        }

        public int getAgeGroup() {
            return ageGroup;
        }

        public void setAgeGroup(int ageGroup) {
            this.ageGroup = ageGroup;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public int getDeaths() {
            return deaths;
        }

        public void setDeaths(int deaths) {
            this.deaths = deaths;
        }

        @Override
        public String toString() {
            return "DeathsPerWeek{" +
                    "year=" + year +
                    ", weekNr=" + weekNr +
                    ", province=" + province +
                    ", ageGroup=" + ageGroup +
                    ", sex=" + sex +
                    ", deaths=" + deaths +
                    '}';
        }
    }
}
