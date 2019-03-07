package com.example.alec.tunetrain.Daos;
import android.arch.persistence.room.*;
import com.example.alec.tunetrain.Entities.Playlist;

import java.util.List;

@Dao
public interface PlaylistDao {
    @Insert
    void insert(Playlist playlist);

    @Query("DELETE FROM Playlists")
    void deleteAll();

    @Query("SELECT * FROM Playlists")
    List<Playlist> getPlaylists();
}