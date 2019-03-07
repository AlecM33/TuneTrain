package com.example.alec.tunetrain.Daos;
import android.arch.persistence.room.*;
import com.example.alec.tunetrain.DatabaseEntities;
import java.util.List;

@Dao
public interface ScaleDao {
    @Insert
    void insert(DatabaseEntities.Scale scale);

    @Query("DELETE FROM Scales")
    void deleteAll();

    @Query("SELECT * FROM Scales")
    List<DatabaseEntities.Scale> getScales();
}