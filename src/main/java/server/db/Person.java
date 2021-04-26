package server.db;

public record Person(
        String vorname,
        String nachname,
        int alter
) {
    // Methods
    public String name() {
        return nachname + " " + vorname;
    }

    public String toString() {
        return "Person{" +
                "vorname='" + vorname + '\'' +
                ", nachname='" + nachname + '\'' +
                ", alter=" + alter +
                '}';
    }
}
