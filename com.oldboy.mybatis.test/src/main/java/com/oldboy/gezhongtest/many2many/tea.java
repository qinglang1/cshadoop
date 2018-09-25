package com.oldboy.gezhongtest.many2many;

import java.util.ArrayList;
import java.util.List;

public class tea {
    private  Integer id;
    private String name;
    private List<stu> stus=new ArrayList<stu>();

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

    public List<stu> getStus() {
        return stus;
    }

    public void setStus(List<stu> stus) {
        this.stus = stus;
    }

    public void addstuduents(stu... duogestu){
        for (stu stu : duogestu) {
            stus.add(stu);
            stu.getTeas().add(this);
        }





    }



}
