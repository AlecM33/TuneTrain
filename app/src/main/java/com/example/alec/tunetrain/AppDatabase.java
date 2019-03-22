package com.example.alec.tunetrain;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.*;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.alec.tunetrain.Daos.*;
import com.example.alec.tunetrain.Entities.*;

import com.example.alec.tunetrain.Daos.TemplateDao;

import java.util.concurrent.Executors;

@Database(entities = {Template.class, Guess.class, Note.class, PlayedNote.class, Playlist.class,
        Scale.class, SpotifyAccount.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {


    public abstract TemplateDao templateDao();
    public abstract SpotifyAccountDao spotifyAccountDao();
    public abstract ScaleDao scaleDao();
    public abstract PlaylistDao playlistDao();
    public abstract NotesPlayedDao notesPlayedDao();
    public abstract NoteDao noteDao();
    public abstract GuessDao guessDao();


    // marking the instance as volatile to ensure atomic access to the variable
    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getAppDatabase(final Context context) {

        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = buildDatabase(context);
                }
            }
        }
        return INSTANCE;
    }

    private static AppDatabase buildDatabase(final Context context) {
        return Room.databaseBuilder(context.getApplicationContext(),
                AppDatabase.class,
                "user-database")
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                            @Override
                            public void run() {
                                Log.d("DB","onCreate(Bundle) database initialized");

                                getAppDatabase(context).templateDao().insertAll(Template.populateData());
                                getAppDatabase(context).noteDao().insertAll(Note.populateData());
                            }
                        });
                    }
                })
                .allowMainThreadQueries()
                .build();
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
