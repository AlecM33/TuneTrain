package com.example.alec.tunetrain.Daos;
import android.arch.persistence.room.*;
import com.example.alec.tunetrain.DatabaseEntities;
import java.util.List;

@Dao
public interface NotesPlayedDao {
    @Insert
    void insert(DatabaseEntities.PlayedNote playedNote);

    @Query("DELETE FROM Notes_Played")
    void deleteAll();

    @Query("SELECT * FROM Notes_Played")
    List<DatabaseEntities.PlayedNote> getPlayedNotes();
}