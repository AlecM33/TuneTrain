package com.example.alec.tunetrain;
import android.arch.persistence.room.*;
import android.content.Context;

import com.example.alec.tunetrain.Daos.*;
import com.example.alec.tunetrain.Entities.*;

import com.example.alec.tunetrain.Daos.TemplateDao;

@Database(entities = {Template.class, Guess.class, Note.class, PlayedNote.class, Playlist.class,
        Scale.class, SpotifyAccount.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract TemplateDao templateDao();
    public abstract SpotifyAccountDao spotifyAccountDao();
    public abstract ScaleDao scaleDao();
    public abstract PlaylistDao playlistDao();
    public abstract NotesPlayedDao notesPlayedDao();
    public abstract NoteDao noteDao();
    public abstract GuessDao guessDao();


    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "user-database")
                            // allow queries on the main thread.
                            // Don't do this on a real app! See PersistenceBasicSample for an example.
                            .allowMainThreadQueries()
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
