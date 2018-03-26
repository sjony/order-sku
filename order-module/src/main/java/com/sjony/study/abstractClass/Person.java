package com.sjony.study.abstractClass;

/**
 * Created by sjony on 2018/3/24.
 */
public abstract class Person {

    public abstract String getDescription();
    private String name;

    public Person(String n) {
        name = n;
    }

    public String getName() {
        return name;
    }
}
