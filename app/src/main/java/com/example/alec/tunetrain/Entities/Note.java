package com.example.alec.tunetrain.Entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.example.alec.tunetrain.R;

import java.util.HashMap;
import java.util.List;

@Entity(tableName = "Notes")
public class Note {
    @PrimaryKey(autoGenerate = true)
    public int noteId;

    @ColumnInfo(name = "note_name")
    public String noteName;

    @ColumnInfo(name = "note_freq")
    public int noteFreq;

    @ColumnInfo(name = "file_name")
    public int fileName;

    public Note(String noteName, int fileName) {
        this.noteName = noteName;
        this.fileName = fileName;
    }

    /*
     * get a Map of String note names to their R.raw filename
     */
    @Ignore
    public static HashMap<String, Integer> getFileMap(List<Note> allNotes) {

        HashMap<String, Integer> fileMap = new HashMap<>();
        for (Note n : allNotes) {
            fileMap.put(n.noteName, n.fileName);
        }

        return fileMap;
    }

    public static Note[] populateData() {
        return new Note[] {
                new Note("A", R.raw.a),
                new Note("Ab", R.raw.ab),
                new Note("Bb", R.raw.bb),
                new Note("B", R.raw.b),
                new Note("C", R.raw.c),
                new Note("C#", R.raw.cs),
                new Note("D", R.raw.d),
                new Note("Eb", R.raw.eb),
                new Note("E", R.raw.e),
                new Note("F", R.raw.f),
                new Note("F#", R.raw.fs),
                new Note("G", R.raw.g)
        };
    }
}