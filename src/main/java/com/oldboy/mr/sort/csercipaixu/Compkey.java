package com.oldboy.mr.sort.csercipaixu;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Compkey implements WritableComparable<Compkey> {
   private String year;
   private int temp;

    public Compkey(String year, int temp) {
        this.year = year;
        this.temp = temp;
    }

    @Override
    public String toString() {
        return "Compkey{" +
                "year='" + year + '\'' +
                ", temp=" + temp +
                '}';
    }

    public Compkey() {
    }

    public Compkey(int temp) {
        this.temp = temp;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public String getYear() {

        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int compareTo(Compkey o) {
        if (this.year.equals(o.year)){
            return -(this.temp-o.temp);
        }
        return  this.year.compareTo(o.year);
    }

    public void write(DataOutput out) throws IOException {
        out.writeUTF(this.year);
        out.writeInt(this.temp);

    }

    public void readFields(DataInput in) throws IOException {
       this.setYear(in.readUTF());
       this.setTemp(in.readInt());

    }


}
