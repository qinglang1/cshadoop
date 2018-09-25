package com.oldboy.mr.csdb;


import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.lib.db.DBWritable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MyWritable implements Writable,DBWritable {
    private  int id;
    private String line;

    public MyWritable() {
    }

    public MyWritable(int id, String line) {
        this.id = id;
        this.line = line;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public void write(DataOutput out) throws IOException {
        out.writeInt(id);
        out.writeUTF(line);
    }

    public void readFields(DataInput in) throws IOException {
        id = in.readInt();
       line = in.readUTF();

    }

    public void write(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setInt(1,id);
        preparedStatement.setString(2,line);

    }

    public void readFields(ResultSet resultSet) throws SQLException {
         id = resultSet.getInt(1);
         line = resultSet.getString(2);
    }
}
