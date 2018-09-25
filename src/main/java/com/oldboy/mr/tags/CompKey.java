package com.oldboy.mr.tags;

public class CompKey implements Comparable<CompKey>{

    private String tag ;
    private int  num ;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public CompKey(String tag, int num) {
        this.tag = tag;
        this.num = num;
    }

    public int compareTo(CompKey o) {
       if(this.num==o.num){
           return  this.tag.compareTo(o.tag);
       }
       return  -(this.num-o.num);
    }

    @Override
    public String toString() {
        return tag+"_"+num;

    }
}



