/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package core.collections.sort;

import core.data.Person;

import java.util.Comparator;

/**
 *
 */
public class ComparePersonAlter implements Comparator<Person> {

    boolean ascending = true;
    
    public ComparePersonAlter(boolean ascending) {
        this.ascending = ascending;
    }
    
    @Override
    public int compare(Person o1, Person o2) {
        if( ascending )
            return o1.getAlter() -  o2.getAlter();
        else
            return o2.getAlter() -  o1.getAlter();
    }
    
}
