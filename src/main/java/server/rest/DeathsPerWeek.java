package server.rest;

public class DeathsPerWeek {
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
