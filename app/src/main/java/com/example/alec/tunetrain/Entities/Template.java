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

    private static String[] getNotesInScale(String[] notes, int noteIndex, Double[] intervals) {
        String[] notesInScale = {};
        int currentDegree = 0;
        for (int i = 0; i < intervals.length; i++) {
            if (noteIndex + currentDegree >= notes.length) {
                notesInScale[i] = notes[noteIndex + currentDegree - notes.length];
            } else {
                notesInScale[i] = notes[noteIndex + currentDegree];
            }
            currentDegree = currentDegree + (int)(2 * intervals[i]);
        }
        return notesInScale;
    }

    private static Template[] generateMajorTemplates() {
        String[] notes = {"A", "Bb", "B", "C", "Db", "D", "Eb", "E", "F", "Gb", "G", "Ab"};
        Double[] intervals = {1.0, 1.0, 0.5, 1.0, 1.0, 1.0, 0.5};
        Template[] majorTemplates = {};
        String[] notesInScale;
        for (int i = 0; i < notes.length; i++) {
            notesInScale = getNotesInScale(notes, i, intervals);
            majorTemplates[i] = new Template(notes[i] + " Major", notesInScale[0],
                    notesInScale[1], notesInScale[2], notesInScale[3], notesInScale[4],
                    notesInScale[5], notesInScale[6], "none", "none", "none",
                    "none", "none");
        }
        return majorTemplates;
    }

    private static Template[] generateMinorTemplates() {
        String[] notes = {"A", "Bb", "B", "C", "Db", "D", "Eb", "E", "F", "Gb", "G", "Ab"};
        Double[] intervals = {1.0, 0.5, 1.0, 1.0, 0.5, 1.0, 1.0};
        Template[] minorTemplates = {};
        String[] notesInScale;
        for (int i = 0; i < notes.length; i++) {
            notesInScale = getNotesInScale(notes, i, intervals);
            minorTemplates[i] = new Template(notes[i] + " Minor", notesInScale[0],
                    notesInScale[1], notesInScale[2], notesInScale[3], notesInScale[4],
                    notesInScale[5], notesInScale[6], "none", "none", "none",
                    "none", "none");
        }
        return minorTemplates;
    }

    private static Template[] generateBluesTemplates() {
        String[] notes = {"A", "Bb", "B", "C", "Db", "D", "Eb", "E", "F", "Gb", "G", "Ab"};
        Double[] intervals = {1.5, 1.0, 0.5, 0.5, 1.5, 1.0};
        Template[] bluesTemplates = {};
        String[] notesInScale;
        for (int i = 0; i < notes.length; i++) {
            notesInScale = getNotesInScale(notes, i, intervals);
            bluesTemplates[i] = new Template(notes[i] + " Blues", notesInScale[0],
                    notesInScale[1], notesInScale[2], notesInScale[3], notesInScale[4],
                    notesInScale[5], "none", "none", "none", "none",
                    "none", "none");
        }
        return bluesTemplates;
    }

    public static Template[] populateMajorScales() {
        return generateMajorTemplates();
    }

    public static Template[] populateMinorScales() {
        return generateMinorTemplates();
    }

    public static Template[] populateBluesScales() {
        return generateBluesTemplates();
    }
}