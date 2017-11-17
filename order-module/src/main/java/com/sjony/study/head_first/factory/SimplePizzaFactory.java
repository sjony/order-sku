package com.sjony.study.head_first.factory;

/**
 * Created by sjony on 2017/9/21.
 */
public class SimplePizzaFactory {

    public Pizza createPizza(String type) {
        Pizza pizza = null;

        if("chese".equals(type)) {
            pizza = new ChesePizza();
        } else if ("clam".equals(type)) {
            pizza = new ClamPizza();
        }

        return pizza;
    }

}
