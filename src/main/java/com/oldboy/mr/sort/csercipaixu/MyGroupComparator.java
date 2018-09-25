package com.oldboy.mr.sort.csercipaixu;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

    public class MyGroupComparator extends WritableComparator {
        protected MyGroupComparator(){super(Compkey.class,true);}

        @Override
        public int compare(WritableComparable a, WritableComparable b) {
            Compkey ck1 = (Compkey) a;
            Compkey ck2 = (Compkey) b;
            return ck1.getYear().compareTo(ck2.getYear());

        }
    }
