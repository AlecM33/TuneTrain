package com.example.alec.tunetrain.Entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "Notes")
public class Note {
    @PrimaryKey(autoGenerate = true)
    public int noteId;

    @ColumnInfo(name = "note_name")
    public String noteName;

    @ColumnInfo(name = "note_freq")
    public int noteFreq;

    public Note(String noteName, int noteFreq) {
        this.noteName = noteName;
        this.noteFreq = noteFreq;
    }
}