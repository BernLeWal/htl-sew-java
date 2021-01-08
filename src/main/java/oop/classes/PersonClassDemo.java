package oop.classes;

/**
 * PersonClassDemo shows how to create and use instances of a class.
 */
public class PersonClassDemo {

    public static void main(String[] args) {
        // Wir erzeugen Instanzen der Klasse Person
        System.out.println(">>>Wir erzeugen Instanzen der Klasse Person");
        Person a = new Person();
        Person b = new Person("Felix", 22, 67);
        Person c = new Person(b);
        c.setName("Mia");
        System.out.println();

        // Wir rufen die reden()-Methoden der Instanzen auf
        System.out.println(">>>Wir rufen die reden()-Methoden der Instanzen auf");
        String s = a.reden();
        System.out.println(s);
        System.out.println(b.reden());
        System.out.println(c.reden());
        System.out.println();

        // Wir rufen die essen()-Methoden der Instanzen auf
        System.out.println(">>>Wir rufen die essen()-Methoden der Instanzen auf");
        a.essen("Pizza");
        b.essen("pizza");
        c.essen("Spaghetti");
        a.essen("Gänseleberpastete");
        System.out.println();

        // Jetzt machen wir einen abschließenden Versuch
        System.out.println(">>>Jetzt machen wir einen abschließenden Versuch");
        Person l = b;
        l.setName("Josi");
        System.out.println("Der Name von l ist " + l.getName());
        System.out.println("Der Name von b ist " + b.getName());
    }
}
