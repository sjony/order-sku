package com.sjony.study.head_first.factory;

/**
 * Created by sjony on 2017/9/21.
 */
public class PizzaStore {

    SimplePizzaFactory pizzaBeanFactory;

    public PizzaStore(SimplePizzaFactory pizzaBeanFactory) {
        this.pizzaBeanFactory = pizzaBeanFactory;
    }

    public Pizza orderPizza(String type) {
        Pizza pizza = pizzaBeanFactory.createPizza(type);
        pizza.preoare();
        pizza.bake();
        pizza.cut();
        pizza.box();
        return pizza;

    }

}
