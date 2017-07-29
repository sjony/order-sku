package com.sjony.Thread;

import com.google.common.collect.Lists;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.util.List;

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

    }

}
