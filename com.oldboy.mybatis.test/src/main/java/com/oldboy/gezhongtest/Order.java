package com.oldboy.gezhongtest;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private  Integer id;
    private  String orderNo;
    private  float price ;
    //建立多对一关联关系
    private  User user ;

    private List<Orderitem> orderitems=new ArrayList<Orderitem>();

    public List<Orderitem> getOrderitems() {
        return orderitems;
    }

    public void setOrderitems(List<Orderitem> orderitems) {
        this.orderitems = orderitems;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
