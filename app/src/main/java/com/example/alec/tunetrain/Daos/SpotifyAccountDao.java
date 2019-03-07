package com.example.alec.tunetrain.Daos;
import android.arch.persistence.room.*;
import com.example.alec.tunetrain.Entities.SpotifyAccount;

import java.util.List;

@Dao
public interface SpotifyAccountDao {
    @Insert
    void insert(SpotifyAccount spotifyAccount);

    @Query("DELETE FROM Spotify_Accounts")
    void deleteAll();

    @Query("SELECT * FROM Spotify_Accounts")
    List<SpotifyAccount> getSpotifyAccounts();
}