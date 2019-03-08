package com.example.alec.tunetrain.Entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "Templates")
public class Template {
    @PrimaryKey(autoGenerate = true)
    public int templateId;

    @ColumnInfo(name = "template_name")
    public String templateName;

    @ColumnInfo(name = "pad1")
    public String pad1;

    @ColumnInfo(name = "pad2")
    public String pad2;

    @ColumnInfo(name = "pad3")
    public String pad3;

    @ColumnInfo(name = "pad4")
    public String pad4;

    @ColumnInfo(name = "pad5")
    public String pad5;

    @ColumnInfo(name = "pad6")
    public String pad6;

    @ColumnInfo(name = "pad7")
    public String pad7;

    @ColumnInfo(name = "pad8")
    public String pad8;

    @ColumnInfo(name = "pad9")
    public String pad9;

    @ColumnInfo(name = "pad10")
    public String pad10;

    @ColumnInfo(name = "pad11")
    public String pad11;

    @ColumnInfo(name = "pad12")
    public String pad12;

    public Template(String templateName, String pad1, String pad2, String pad3, String pad4, String pad5, String pad6,
                    String pad7, String pad8, String pad9, String pad10, String pad11, String pad12){
        this.templateName = templateName;
        this.pad1 = pad1;
        this.pad2 = pad2;
        this.pad3 = pad3;
        this.pad4 = pad4;
        this.pad5 = pad5;
        this.pad6 = pad6;
        this.pad7 = pad7;
        this.pad8 = pad8;
        this.pad9 = pad9;
        this.pad10 = pad10;
        this.pad11 = pad11;
        this.pad12 = pad12;
    }

    @Ignore
    public String[] getPads() {
        String[] pads = {
                this.pad1,
                this.pad2,
                this.pad3,
                this.pad4,
                this.pad5,
                this.pad6,
                this.pad7,
                this.pad8,
                this.pad9,
                this.pad10,
                this.pad11,
                this.pad12,
        };
        return pads;
    }
}