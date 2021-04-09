package server.rest.opendata;

import javafx.util.Pair;
import patterns.parser.CsvParser;

import java.io.IOException;
import java.util.Arrays;
import java.net.URL;

/**
 * TyrolDeathsPerDistrictSince2010 accesses the following catalog of "Open Data Österreich" (https://www.data.gv.at/):
 * Katalog Sterbefälle nach Geschlecht und Altersgruppen in Tirol
 * (https://www.data.gv.at/katalog/dataset/bd1a2cb8-8942-475d-9d0d-44c21194b660)
 */
public class TyrolDeathsPerDistrictSince2010 {
    public static void main(String[] args) throws IOException {
        var urls = Arrays.asList(
                new Pair<>(2010, "https://gis.tirol.gv.at/ogd/bevoelkerung/Gestorbene_nach_Alter_Geschlecht_2010.csv"),
                new Pair<>(2011, "https://gis.tirol.gv.at/ogd/bevoelkerung/Gestorbene_nach_Alter_Geschlecht_2011.csv"),
                new Pair<>(2012, "https://gis.tirol.gv.at/ogd/bevoelkerung/Gestorbene_nach_Alter_Geschlecht_2012.csv"),
                new Pair<>(2013, "https://gis.tirol.gv.at/ogd/bevoelkerung/Gestorbene_nach_Alter_Geschlecht_2013.csv"),
                new Pair<>(2014, "https://gis.tirol.gv.at/ogd/bevoelkerung/Gestorbene_nach_Alter_Geschlecht_2014.csv"),
                new Pair<>(2015, "https://gis.tirol.gv.at/ogd/bevoelkerung/Gestorbene_nach_Alter_Geschlecht_2015.csv"),
                new Pair<>(2016, "https://gis.tirol.gv.at/ogd/bevoelkerung/Gestorbene_nach_Alter_Geschlecht_2016.csv"),
                new Pair<>(2017, "https://gis.tirol.gv.at/ogd/bevoelkerung/Gestorbene_nach_Alter_Geschlecht_2017.csv"),
                new Pair<>(2018, "https://gis.tirol.gv.at/ogd/bevoelkerung/Gestorbene_nach_Alter_Geschlecht_2018.csv"),
                new Pair<>(2019, "https://gis.tirol.gv.at/ogd/bevoelkerung/Gestorbene_nach_Alter_Geschlecht_2019.csv"),
                new Pair<>(2020, "https://gis.tirol.gv.at/ogd/bevoelkerung/Gestorbene_nach_Alter_Geschlecht_2020.csv")
        );

        for ( var item : urls ) {
            try {
                int deathsPerYear = readTotalDeaths(item.getValue());
                System.out.printf("%s %d\n", item.getKey(), deathsPerYear);
            }
            catch( Exception e ) {
                System.err.println( "Could not evaluate " + item.getKey() + " url " + item.getValue() + " reason " + e.getMessage());
            }
        }
    }

    private static int readTotalDeaths(String url) throws IOException {
        // explanations to calculation with stream-API: https://www.baeldung.com/java-stream-sum
        return new CsvParser(';')
                .parse( new URL(url).openConnection().getInputStream() ).stream()
                .map(
                        row -> new RawData(
                                row.get(3),                     // DISTRICT_CODE
                                row.get(4),                     // NAME
                                row.get(5),                     // AGE_GRP
                                Integer.parseInt(row.get(8))    // DEATH_TOTAL
                        )
                )
                .filter( record -> record.districtCode.equals("70926" ) )   // Schwaz
                .mapToInt( row -> row.deathsTotal ).sum();
    }

    public static record RawData(
            String districtCode,
            String districtName,
            String ageGroup,
            int deathsTotal
    ) {}
}
