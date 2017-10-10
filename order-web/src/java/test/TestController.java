package test;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

public class TestController  {

    public static void main(String[] args) throws Exception {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath*:test.xml");
        //        ChesePizza chesePizza  = (ChesePizza) context.getBean("test");
        Person p = ctx.getBean("person", Person.class);
        Person p1 = ctx.getBean("person1", Person.class);
        Person p2 = ctx.getBean("person2", Person.class);
        Map<String, Person> a = ctx.getBeansOfType(Person.class);
        //        ChesePizza chesePizza111  = c、ontext.getBean(ChesePizza.class);
        String s = null;
    }

}
