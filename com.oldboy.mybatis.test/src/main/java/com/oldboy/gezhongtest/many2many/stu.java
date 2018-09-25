package com.oldboy.gezhongtest.many2many;

import java.util.ArrayList;
import java.util.List;

public class stu {

    private  Integer id;
    private String name;
    private List<tea> teas=new ArrayList<tea>();

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

    public List<tea> getTeas() {
        return teas;
    }

    public void setTeas(List<tea> teas) {
        this.teas = teas;
    }
}
