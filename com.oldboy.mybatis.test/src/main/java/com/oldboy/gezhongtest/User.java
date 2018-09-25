package com.oldboy.gezhongtest;

import java.util.ArrayList;
import java.util.List;

public class User {
private Integer id;
private  String name;
private  int  age;
private List<Order> orders=new ArrayList<Order>();

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void addOrders(Order... orders1){
        for (Order order : orders1) {
            orders.add(order);
            order.setUser(this);
        }
    }
}
