package com.example.alec.tunetrain.Entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "Guesses")
public class Guess {
    @PrimaryKey(autoGenerate = true)
    public int guessId;

    @ColumnInfo(name = "game_mode")
    public String gameMode;

    @ColumnInfo(name = "correct")
    public boolean correct;

    @ColumnInfo(name = "start_of_session")
    public boolean startOfSession;

    public Guess(String gameMode, boolean correct, boolean startOfSession) {
        this.gameMode = gameMode;
        this.correct = correct;
        this.startOfSession = startOfSession;
    }
}
