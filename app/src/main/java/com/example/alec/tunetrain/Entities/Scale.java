package com.example.alec.tunetrain.Entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "Scales")
public class Scale {
    @PrimaryKey
    public int scaleId;

    @ColumnInfo(name = "scale_type")
    public String scaleType;

    @ColumnInfo(name = "intervals")
    public String intervals;

    public Scale(String scaleType, String intervals) {
        this.scaleType = scaleType;
        this.intervals = intervals;
    }
}
