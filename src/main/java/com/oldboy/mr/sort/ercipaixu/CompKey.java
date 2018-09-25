package com.oldboy.mr.sort.ercipaixu;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * //解释很重要
 * //  WritableComparable是接口， WritableComparator是类
 *  1.public interface WritableComparable<T> extends Writable, Comparable<T> {
 *  }
 *  2.public interface Comparable<T> {
 *   public int compareTo(T o);
 *  }
 *  3.public interface Writable {
 *  void write(DataOutput out) throws IOException;  //<code>DataOuput</code> to serialize this object into.
 *  void readFields(DataInput in) throws IOException;  //<code>DataInput</code> to deseriablize this object from.
  */

public class CompKey implements WritableComparable<CompKey> {

    private String year;
    private int temp;

    //重写comparable接口中的compareTo方法  定义排序规则
    public int compareTo(CompKey o) {

        String oyear = o.getYear();
        String tyear = this.getYear();
        int otemp = o.getTemp();
        int ttemp = this.getTemp();

        //如果参数year 和现在的year相同，则比较temp的大小
        if (tyear.equals(oyear)) {
            return otemp - ttemp;
        }
        //不同，返回两个year的比较值
        return oyear.compareTo(tyear);
    }

    public void write(DataOutput out) throws IOException {
        out.writeUTF(year);
        out.writeInt(temp);
    }

    public void readFields(DataInput in) throws IOException {
        this.setYear(in.readUTF());
        this.setTemp(in.readInt());

    }


    @Override
    public String toString() {
        return "CompKey{" +
                "year='" + year + '\'' +
                ", temp=" + temp +
                '}';
    }

    public CompKey(String year, int temp) {
        this.year = year;
        this.temp = temp;
    }

    public CompKey() {
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }
}
