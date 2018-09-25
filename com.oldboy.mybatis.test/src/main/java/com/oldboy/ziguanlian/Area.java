package com.oldboy.ziguanlian;

import java.util.ArrayList;
import java.util.List;

public class Area {
    private Integer id;//原生字段
    private  String areaname;//原生字段
    private Area parentarea;//上级关联字段
    private List<Area> childareas=new ArrayList<Area>();//下级关联字段

    public Area() {
    }

    public Area(String areaname) {
        this.areaname = areaname;
    }

    public  void  addchildareas(Area... childareas1){

        for (Area childarea : childareas1) {
            childareas.add(childarea);
            childarea.setParentarea(this);
        }
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAreaname() {
        return areaname;
    }

    public void setAreaname(String areaname) {
        this.areaname = areaname;
    }

    public Area getParentarea() {
        return parentarea;
    }

    public void setParentarea(Area parentarea) {
        this.parentarea = parentarea;
    }

    public List<Area> getChildareas() {
        return childareas;
    }

    public void setChildareas(List<Area> childareas) {
        this.childareas = childareas;
    }


}
