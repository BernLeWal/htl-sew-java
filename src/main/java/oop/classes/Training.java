package oop.classes;

public class Training {
    // Attribute:

    private int datumJahr, datumMonat, datumTag;
    private int lage;    // 0...Brust; 1...Kraulen; 2... Delphin
    private int laenge;  // in Metern 
    private int dauerMin, dauerSec;  // Zeit
    private String notizen;

    // Konstruktoren:
    public Training(
            int datumJahr, int datumMonat, int datumTag,
            int lage,
            int laenge,
            int dauerMin, int dauerSec,
            String notizen) {
        this.datumJahr = datumJahr;
        this.datumMonat = datumMonat;
        this.datumTag = datumTag;
        this.lage = lage;
        this.laenge = laenge;
        this.dauerMin = dauerMin;
        this.dauerSec = dauerSec;
        this.notizen = notizen;
    }

    public int getDatumJahr() {
        return datumJahr;
    }

    public int getDatumMonat() {
        return datumMonat;
    }

    public int getDatumTag() {
        return datumTag;
    }

    public int getLage() {
        return lage;
    }

    public int getLaenge() {
        return laenge;
    }

    public int getDauerMin() {
        return dauerMin;
    }

    public int getDauerSec() {
        return dauerSec;
    }

    public String getNotizen() {
        return notizen;
    }


}
