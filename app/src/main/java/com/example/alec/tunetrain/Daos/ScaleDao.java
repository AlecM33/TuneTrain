package com.example.alec.tunetrain.Daos;
import android.arch.persistence.room.*;
import com.example.alec.tunetrain.Entities.Scale;

import java.util.List;

@Dao
public interface ScaleDao {
    @Insert
    void insert(Scale scale);

    @Query("DELETE FROM Scales")
    void deleteAll();

    @Query("SELECT * FROM Scales")
    List<Scale> getScales();
}