package server.rest;

public class CalculatedDeathsPerYear {
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
