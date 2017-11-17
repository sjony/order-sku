package com.sjony.study.Thread;

import com.google.common.collect.Lists;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by sjony on 2017/7/29.
 */
public class ThreadJoin {


    public static void main(String[] args) throws InterruptedException {

        List<String> a = Lists.newArrayList();

        Thread t1 = new Thread(){

            public void run() {
                a.add("A");
            }

        };

        Thread t2 = new Thread(){

            public void run() {

                a.add("B");
            }

        };
        t1.start();
        t2.start();
        t2.join();
        a.add("C");
        for(String s : a) {
            System.out.println(s);
        }
        List<String> aaa = new ArrayList<String>();
        aaa.add("23");
        String s = getNum(aaa);

    }


    private static <T>T getNum(List<T> t) {
        return t.get(0);
    }

}
