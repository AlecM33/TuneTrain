package com.example.alec.tunetrain.Daos;
import android.arch.persistence.room.*;
import com.example.alec.tunetrain.Entities.PlayedNote;

import java.util.List;

@Dao
public interface NotesPlayedDao {
    @Insert
    void insert(PlayedNote playedNote);

    @Query("DELETE FROM Notes_Played")
    void deleteAll();

    @Query("SELECT * FROM Notes_Played")
    List<PlayedNote> getPlayedNotes();
}