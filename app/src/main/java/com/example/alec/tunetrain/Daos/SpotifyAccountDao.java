package com.example.alec.tunetrain.Daos;
import android.arch.persistence.room.*;
import com.example.alec.tunetrain.DatabaseEntities;
import java.util.List;

@Dao
public interface SpotifyAccountDao {
    @Insert
    void insert(DatabaseEntities.SpotifyAccount spotifyAccount);

    @Query("DELETE FROM Spotify_Accounts")
    void deleteAll();

    @Query("SELECT * FROM Spotify_Accounts")
    List<DatabaseEntities.SpotifyAccount> getSpotifyAccounts();
}