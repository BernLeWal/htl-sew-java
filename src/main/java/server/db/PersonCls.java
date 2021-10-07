package server.db;

public class PersonCls {
    private final String vorname;
    private final String nachname;
    private final int alter;

    public PersonCls(String vorname,
                     String nachname,
                     int alter) {
        this.vorname = vorname;
        this.nachname = nachname;
        this.alter = alter;
    }

    public String vorname() {
        return vorname;
    }

    public String nachname() {
        return nachname;
    }

    public int alter() {
        return alter;
    }
}
