package com.example.alec.tunetrain.Entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

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

    public Note(String noteName, int noteFreq) {
        this.noteName = noteName;
        this.noteFreq = noteFreq;
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
}