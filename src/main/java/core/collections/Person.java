package core.collections;

/**
 * Every instance represents the basic information about a person.
 */
public class Person {
    // Attributes
    private int nr;
    private String vn;
    private String nn;

    // Constructors
    public Person(int nr, String vn, String nn) {
        this.nr = nr;
        this.vn = vn;
        this.nn = nn;
    }

    // Getters- & Setters
    public int getNr() {
        return nr;
    }

    public String getVn() {
        return vn;
    }

    public String getNn() {
        return nn;
    }

    @Override
    public String toString() {
        return "Person{" +
                "nr=" + nr +
                ", vn='" + vn + '\'' +
                ", nn='" + nn + '\'' +
                '}';
    }
}
