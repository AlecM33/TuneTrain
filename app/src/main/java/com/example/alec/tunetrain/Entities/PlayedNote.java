package com.example.alec.tunetrain.Entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "Notes_Played")
public class PlayedNote {
    @PrimaryKey(autoGenerate = true)
    public int playId;

    @ColumnInfo(name = "note_id")
    public int noteId;

    public PlayedNote(int noteId) {
        this.noteId = noteId;
    }
}
