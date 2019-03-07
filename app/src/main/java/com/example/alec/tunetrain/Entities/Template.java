package com.example.alec.tunetrain.Entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "Templates")
public class Template {
    @PrimaryKey(autoGenerate = true)
    public int templateId;

    @ColumnInfo(name = "template_name")
    public String templateName;

    @ColumnInfo(name = "pad1")
    public int pad1;

    @ColumnInfo(name = "pad2")
    public int pad2;

    @ColumnInfo(name = "pad3")
    public int pad3;

    @ColumnInfo(name = "pad4")
    public int pad4;

    @ColumnInfo(name = "pad5")
    public int pad5;

    @ColumnInfo(name = "pad6")
    public int pad6;

    @ColumnInfo(name = "pad7")
    public int pad7;

    @ColumnInfo(name = "pad8")
    public int pad8;

    @ColumnInfo(name = "pad9")
    public int pad9;

    @ColumnInfo(name = "pad10")
    public int pad10;

    @ColumnInfo(name = "pad11")
    public int pad11;

    @ColumnInfo(name = "pad12")
    public int pad12;

    public Template(String templateName, int pad1, int pad2, int pad3, int pad4, int pad5, int pad6,
                    int pad7, int pad8, int pad9, int pad10, int pad11, int pad12){
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
}