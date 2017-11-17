package test;


import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.math.BigDecimal;
import java.text.Bidi;
import java.util.HashMap;
import java.util.Map;

public class TestController  {

    public static void main(String[] args) throws Exception {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath*:test.xml");
        //        ChesePizza chesePizza  = (ChesePizza) context.getBean("test");
        Person p = ctx.getBean("person", Person.class);
        Person p1 = ctx.getBean("person1", Person.class);
        Person p2 = ctx.getBean("person2", Person.class);
        //        ChesePizza chesePizza111  = c、ontext.getBean(ChesePizza.class);
        String s = null;

        /*String s = "aaa";
        boolean sd = false;
        boolean ssss = Boolean.FALSE.equals(sd);
        String b = "dfffaaaccc";*/


        /*BigDecimal a = new BigDecimal(1);
        BigDecimal b = a;
        BigDecimal c = a;
        a = a.subtract(BigDecimal.ONE);
        Person p1 = new Person();
        p1.setName("111");
        Person p2 = p1;
        p1.setName("222");
        System.out.print(b);*/

       /* Person p = new Person();
        p.setName("123");
        p.setAge(123);
        Person p1 = new Person();
        p1.setName("123");
        p1.setAge(123);
        Boolean nameB = (p.getName() == "123");
        Boolean ageB = (p.getAge() == p1.getAge());
        String s = null;*/
    }


    private static void test(Person p1) {

        Person p2 = new Person();
        p2.setName("222");

        p1 = p2;
    }

}
