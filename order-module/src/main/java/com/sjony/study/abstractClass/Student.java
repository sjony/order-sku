package com.sjony.study.abstractClass;

/**
 * Created by sjony on 2018/3/24.
 */
public class Student extends Person {

    private String major;

    public Student(String n, String m) {
        super(n);
        major = m;
    }

    public String getDescription() {
        return "a student majoring in " + major;
    }
}
