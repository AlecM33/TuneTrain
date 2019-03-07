package com.example.alec.tunetrain.Entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "Playlists")
public class Playlist {
    @PrimaryKey(autoGenerate = true)
    public int playlistId;

    @ColumnInfo(name = "playlist_name")
    public String playlistName;

    @ColumnInfo(name = "spotify_uri")
    public String spotifyUri;

    public Playlist(String playlistName, String spotifyUri) {
        this.playlistName = playlistName;
        this.spotifyUri = spotifyUri;
    }
}
