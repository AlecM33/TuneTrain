package com.example.alec.tunetrain.Daos;
import android.arch.persistence.room.*;
import com.example.alec.tunetrain.Entities.Note;

import java.util.List;

@Dao
public interface NoteDao {
    @Insert
    void insert(Note note);

    @Query("DELETE FROM Notes")
    void deleteAll();

    @Query("SELECT * FROM Notes")
    List<Note> getNotes();
}