package com.sjony.study.dy;

import org.apache.commons.io.FileUtils;

import java.io.FileDescriptor;
import java.lang.reflect.Field;

/**
 * Created by sjony on 2018/3/24.
 */

class Person {

    public String name;
    private int age;

    public Person(String name, int age) {
        this.age = age;
        this.name = name;
    }

    //重写equals方法，规定只要Person的所有属性值相等，两个对象就相等
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Person) {
            try {
                Boolean check = true;
                Field[] objectFields = obj.getClass().getDeclaredFields();
                Field[] personFields = obj.getClass().getDeclaredFields();

                for (int i = 0; i < objectFields.length; i++) {
                    if (!objectFields[i].get(obj).equals(personFields[i].get(this))) {
                        check = false;
                        break;
                    }
                }
                return check;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

/*
    //重写hashCode方法，把对象的name和age属性转为一个字符串，返回字符串的hashCode值
    public int hashCode() {
        String id = this.name + this.age + "";
        return id.hashCode();
    }
*/
}
public class ObjectDemo {
    public static  void main(String[] args) throws IllegalArgumentException, IllegalAccessException {
        Person p1 = new Person("dy", 18);
        Person p2 = new Person("dy", 18);

        System.out.println(p1.equals(p2));
        System.out.println(p1.hashCode() == p2.hashCode());
        System.out.println(p1.hashCode());
        System.out.println(p2.hashCode());
    }
}
