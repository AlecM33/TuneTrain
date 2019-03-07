package com.example.alec.tunetrain.Daos;
import android.arch.persistence.room.*;
import com.example.alec.tunetrain.DatabaseEntities;
import java.util.List;

@Dao
public interface NoteDao {
    @Insert
    void insert(DatabaseEntities.Note note);

    @Query("DELETE FROM Notes")
    void deleteAll();

    @Query("SELECT * FROM Notes")
    List<DatabaseEntities.Note> getNotes();
}