package com.example.alec.tunetrain.Entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "Spotify_Accounts")
public class SpotifyAccount {
    @PrimaryKey(autoGenerate = true)
    public int accountId;

    @ColumnInfo(name = "username")
    public String username;

    @ColumnInfo(name = "password")
    public String password;

    @ColumnInfo(name = "is_linked")
    public boolean isLinked;

    public SpotifyAccount(String username, String password, boolean isLinked) {
        this.username = username;
        this.password = password;
        this.isLinked = isLinked;
    }
}
