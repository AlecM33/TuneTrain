package com.example.alec.tunetrain.Daos;
import android.arch.persistence.room.*;
import com.example.alec.tunetrain.Entities.Guess;

import java.util.List;

@Dao
public interface GuessDao {
    @Insert
    void insert(Guess guess);

    @Query("DELETE FROM Guesses")
    void deleteAll();

    @Query("SELECT * FROM Guesses")
    List<Guess> getGuesses();
}