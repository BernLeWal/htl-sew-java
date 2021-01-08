/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package core.collections.sort;

import core.data.Person;

import java.util.Comparator;

public class ComparePersonNameDescending implements Comparator<Person> {

    @Override
    public int compare(Person o1, Person o2) {
        return o2.name().compareTo(o1.name());
    }
    
}
