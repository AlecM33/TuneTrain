package com.example.alec.tunetrain.Entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.util.Log;

import java.util.HashMap;

@Entity(tableName = "Templates")
public class Template {
    @PrimaryKey(autoGenerate = true)
    public int templateId;

    @ColumnInfo(name = "template_name")
    public String templateName;

    @ColumnInfo(name = "pad1")
    public Note pad1;

    @ColumnInfo(name = "pad2")
    public Note pad2;

    @ColumnInfo(name = "pad3")
    public Note pad3;

    @ColumnInfo(name = "pad4")
    public Note pad4;

    @ColumnInfo(name = "pad5")
    public Note pad5;

    @ColumnInfo(name = "pad6")
    public Note pad6;

    @ColumnInfo(name = "pad7")
    public Note pad7;

    @ColumnInfo(name = "pad8")
    public Note pad8;

    @ColumnInfo(name = "pad9")
    public Note pad9;

    @ColumnInfo(name = "pad10")
    public Note pad10;

    @ColumnInfo(name = "pad11")
    public Note pad11;

    @ColumnInfo(name = "pad12")
    public Note pad12;

    public Template(String templateName, Note pad1, Note pad2, Note pad3, Note pad4, Note pad5, Note pad6,
                    Note pad7, Note pad8, Note pad9, Note pad10, Note pad11, Note pad12){
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

    public Template(String templateName, String pad1, String pad2, String pad3, String pad4, String pad5, String pad6,
                    String pad7, String pad8, String pad9, String pad10, String pad11, String pad12){
        NoteFiles n = NoteFiles.getNoteFilesInstance();
        HashMap<String, Integer> map = n.getMap();
        this.templateName = templateName;
        this.pad1 = new Note(pad1, map.get(pad1));
        this.pad2 = new Note(pad2, map.get(pad2));
        this.pad3 = new Note(pad3, map.get(pad3));
        this.pad4 = new Note(pad4, map.get(pad4));
        this.pad5 = new Note(pad5, map.get(pad5));
        this.pad6 = new Note(pad6, map.get(pad6));
        this.pad7 = new Note(pad7, map.get(pad7));
        this.pad8 = new Note(pad8, map.get(pad8));
        this.pad9 = new Note(pad9, map.get(pad9));
        this.pad10 = new Note(pad10, map.get(pad10));
        this.pad11 = new Note(pad11, map.get(pad11));
        this.pad12 = new Note(pad12, map.get(pad12));

    }

    @Ignore
    public Note[] getPads() {
        Note[] pads = {
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
        String[] notesInScale = new String[intervals.length];
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
        Template[] majorTemplates = new Template[12];
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
        Template[] minorTemplates = new Template[12];
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
        Log.d("blues called", "THE FUNCTION WAS CALLED");
        String[] notes = {"A", "Bb", "B", "C", "Db", "D", "Eb", "E", "F", "Gb", "G", "Ab"};
        Double[] intervals = {1.5, 1.0, 0.5, 0.5, 1.5, 1.0};
        Template[] bluesTemplates = new Template[12];
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

    public static Template[] populateChromaticScale() {
        return new Template[] {
                new Template("Chromatic", "A", "Bb", "B", "C",
                        "C#", "D", "Eb", "E", "F", "F#", "G", "Ab")
        };
    }
}