package com.oldboy.mr.db;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.lib.db.DBWritable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 *1.   public interface Writable {
 *      void write(DataOutput out) throws IOException;
 *      void readFields(DataInput in) throws IOException;
 *    }
 *
 *2.   public interface DBWritable {
 *      void write(PreparedStatement var1) throws SQLException;
 *
 *      void readFields(ResultSet var1) throws SQLException;
 *    }
 *
 */


public class MyWritable implements Writable, DBWritable {

    private int id;
    private String line;

    public void write(DataOutput out) throws IOException {
        out.writeInt(id);
        out.writeUTF(line);

    }

    public void readFields(DataInput in) throws IOException {
        id = in.readInt();
        line = in.readUTF();

    }

    //DB串行化
    public void write(PreparedStatement st) throws SQLException {
        st.setInt(1,id);
        st.setString(2,line);

    }

    //db反串行,从database中读取数据
    public void readFields(ResultSet rs) throws SQLException {
        id = rs.getInt(1);
        line = rs.getString(2);

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
}