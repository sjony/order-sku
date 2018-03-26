package com.sjony.study.interfaces;
import java.util.*;

/**
 * Created by sjony on 2018/3/24.
 */
public class EmployeeSortTest {
    public static void main(String[] args) {
        Employee[] staff = new Employee[3];

        staff[0] = new Employee("HH", 35000);
        staff[1] = new Employee("CC", 75000);
        staff[2] = new Employee("TT", 38000);

        Arrays.sort(staff);

        for (Employee e : staff) {
            System.out.println("name = " + e.getName() + ",salary = " + e.getSalary());
        }
    }
}
