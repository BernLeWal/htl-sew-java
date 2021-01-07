package core.collections;

import core.data.Person;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * CollectionsDemo shows in a simple very straight-forward way
 * the usage of the most common collections: ArrayList, HashSet and HashMap
 * in terms of creating, iterating, modifying and deletion.
 */
public class CollectionsDemo {
    public static void main(String[] args) {
        listDemo();
        setDemo();
        mapDemo();
    }

    private static void listDemo() {
        System.out.println("********** List demo **********");
        ArrayList<Person> persons = new ArrayList<>();

        System.out.println("\nadd elements:");
        persons.add(new Person(1, "Romeo", "Albrecht"));
        persons.add(new Person(6, "Patrick", "Oboni"));
        persons.add(new Person(7, "Cosmin", "Sauermann"));

        System.out.println("\nread/show elements:");
        for (Person p : persons) {
            System.out.println(p);
        }

        System.out.println("\nfetch one element:");
        System.out.println("Element on index 1 (position 2): " + persons.get(1));

        System.out.println("\ninsert element on index 1 (position 2):");
        final Person richard = new Person(3, "Richard", "Dürl");
        persons.add(1, richard);
        for (Person p : persons) {
            System.out.println(p);
        }

        System.out.println("\nRemove element on index 2 (position 3):");
        persons.remove(2);
        for (Person p : persons) {
            System.out.println(p);
        }

        System.out.println("Remove specific element: " + richard);
        persons.remove(richard);
        for (Person p : persons) {
            System.out.println(p);
        }
    }

    private static void setDemo() {
        System.out.println("********** Set demo **********");
        HashSet<Person> persons = new HashSet<>();

        System.out.println("\nadd elements:");
        persons.add(new Person(1, "Romeo", "Albrecht"));
        persons.add(new Person(6, "Patrick", "Oboni"));
        persons.add(new Person(7, "Cosmin", "Sauermann"));

        System.out.println("\nread/show elements:");
        for (Person p : persons) {
            System.out.println(p);
        }

        System.out.println("\ninsert element:");
        final Person richard = new Person(3, "Richard", "Dürl");
        persons.add(richard);
        for (Person p : persons) {
            System.out.println(p);
        }

        System.out.println("\nfetch one element: --> no way");
        System.out.println("\ncheck if an element is contained in the set: " + persons.contains(richard));
        System.out.println("\ncheck if new element is contained in the set: " + persons.contains(new Person(3, "Richard", "Dürl")));

        System.out.println("\nRemove on element:");
        persons.remove(richard);
        for (Person p : persons) {
            System.out.println(p);
        }

    }

    private static void mapDemo() {
        System.out.println("********** Map demo **********");
        HashMap<Integer, Person> persons = new HashMap<>();

        System.out.println("\nadd elements:");
        final Person romeo = new Person(1, "Romeo", "Albrecht");
        persons.put(1, romeo);
        final Person patrick = new Person(6, "Patrick", "Oboni");
        persons.put(6, patrick);
        final Person cosmin = new Person(7, "Cosmin", "Sauermann");
        persons.put(7, cosmin);

        System.out.println("\nread/show elements:");
        for (Integer nr : persons.keySet()) {
            System.out.println(nr + ": " + persons.get(nr));
        }
        System.out.println("\nget element by key-value");
        System.out.println(persons.get(6));
        System.out.println(persons.get(-1));

        System.out.println("\nremove an element by key:");
        persons.remove(6);
        for (Integer nr : persons.keySet()) {
            System.out.println(nr + ": " + persons.get(nr));
        }

        System.out.println("\n\n********** Multi-Map demo **********");
        HashMap<String, ArrayList<Person>> personGroups = new HashMap<>();

        System.out.println("\ncreate a list for devs and put it into map");
        personGroups.put("Devs", new ArrayList<>());
        System.out.println("add persons to the list");
        var devs = personGroups.get("Devs");
        devs.add(romeo);
        devs.add(cosmin);

        System.out.println("\ncrete a list for hackers and put it into map");
        personGroups.put("Hackers", new ArrayList<>());
        System.out.println("add persons to the list");
        var hackers = personGroups.get("Hackers");
        hackers.add(romeo);
        hackers.add(cosmin);
        hackers.add(patrick);

        System.out.println("\nshow all lists with their content");
        for (String listName : personGroups.keySet()) {
            final ArrayList<Person> listPersons = personGroups.get(listName);
            System.out.println(listName + ": " + listPersons);
            for (Person p : listPersons) {
                System.out.println(p);
            }
        }

    }

}
