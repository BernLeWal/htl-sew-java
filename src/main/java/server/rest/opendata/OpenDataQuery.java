package server.rest.opendata;

import patterns.parser.CsvParser;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * OpenDataQuery accesses the following catalog of "Open Data Österreich" (https://www.data.gv.at/):
 *
 * @param <T> The record-class for one line of data representing the correct data-types
 */
public class OpenDataQuery<T> {
    private final String url;

    private ArrayList<ArrayList<String>> rawTable;
    private ArrayList<T> dataTable;

    /**
     * Create an instance os the OpenData-Tool
     *
     * @param url The download-URL of the resource.
     *            ATTENTION: downloading from open-data only with HTTPS!
     */
    public OpenDataQuery(String url) {
        this.url = url;
    }

    /**
     * STEP 1: Download the CSV file
     *
     * @throws IOException occurs when reading from OpenData webservice fails
     */
    public OpenDataQuery<T> readCSV() throws IOException {
        rawTable = new CsvParser(';').parse(new URL(url).openConnection().getInputStream());
        return this;
    }

    public ArrayList<ArrayList<String>> getRawTable() {
        return rawTable;
    }

    /**
     * STEP 2: convert plain strings into numbers (to be able to evaluate information)
     *
     * @param generatorFunc provided function to transform a row of raw-data to your class
     * @return this
     */
    public OpenDataQuery<T> transformData(Function<ArrayList<String>, T> generatorFunc) {
        dataTable = new ArrayList<>();
        for (var row : rawTable) {

            dataTable.add(generatorFunc.apply(row));
        }
        return this;
    }

    /**
     * STEP 3: filter data
     *
     * @param predicateFunc provided function to filter out interesting rows
     * @return this
     */
    public OpenDataQuery<T> filter(Predicate<T> predicateFunc) {
        ArrayList<T> filteredTable = new ArrayList<>();
        for (var record : dataTable) {
            if (predicateFunc.test(record))
                filteredTable.add(record);
        }
        dataTable = filteredTable;
        return this;
    }

    public ArrayList<T> getDataTable() {
        return dataTable;
    }
}
