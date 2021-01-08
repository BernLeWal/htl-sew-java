/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package core.collections.sort;

import core.data.Person;

import java.util.ArrayList;
import java.util.Collections;

/**
 * SortPersonsDemo demonstrates how the Comparable interface is used to self define sort orders
 */
public class SortPersonsDemo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArrayList<Person> list = new ArrayList<>();
        list.add(new Person(1, "Anton", "Bauer", 18));
        list.add(new Person(2, "Berta", "Cäsar", 17));
        list.add(new Person(3, "Christian", "Maier", 17));
        list.add(new Person(4, "Bernhard", "Wallner", 39));
        System.out.println(list);  // unsortiert

        Collections.sort(list);     // sortiert nach Alter (aufsteigend)
        System.out.println(list);  // sortiert

        list.sort(new ComparePersonAlter(false));     // sortiert nach Alter (absteigend)
        System.out.println(list);  // sortiert

        list.sort(new ComparePersonNameDescending());  // sortiert nach Name (absteigend)
        System.out.println(list);

    }

}
