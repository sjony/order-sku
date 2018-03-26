package com.sjony.study.abstractClass;

import javax.swing.Popup;

/**
 * Created by sjony on 2018/3/24.
 */
public class PersonTest {
    public static void main(String[] args) {
        Person[] people = new Person[2];

        people[0] = new Employee("HH", 50000, 1989, 10, 1);
        people[1] = new Student("MM", "computer science");

        for (Person p : people) {
            System.out.println(p.getName() + ", " + p.getDescription());
        }
    }
}
