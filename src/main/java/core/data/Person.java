package core.data;

import java.io.Serializable;

/**
 * Every instance represents the basic information about a person.
 */
public class Person implements Serializable, Comparable<Person> {
    // Attributes
    private int nr;
    private String vn;
    private String nn;
    private int alter;

    // Constructors
    public Person(int nr, String vn, String nn, int alter) {
        this.nr = nr;
        this.vn = vn;
        this.nn = nn;
        this.alter = alter;
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

    public int getAlter() {
        return alter;
    }

    // Methods
    public String name() {
        return nn + " " + vn;
    }

    @Override
    public String toString() {
        return "Person{" +
                "nr=" + nr +
                ", vn='" + vn + '\'' +
                ", nn='" + nn + '\'' +
                ", alter=" + alter +
                '}';
    }

    // from Comparable<Person>
    @Override
    public int compareTo(Person that) {
        return this.alter - that.alter;
    }
}
