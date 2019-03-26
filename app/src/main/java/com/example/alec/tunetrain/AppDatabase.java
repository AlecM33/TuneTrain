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
@TypeConverters({Converters.class})
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
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "word_database")
                            // Wipes and rebuilds instead of migrating if no Migration object.
                            // Migration is not part of this codelab.
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Override the onOpen method to populate the database.
     * For this sample, we clear the database every time it is created or opened.
     *
     * If you want to populate the database only when the database is created for the 1st time,
     * override RoomDatabase.Callback()#onCreate
     */
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            // If you want to keep the data through app restarts,
            // comment out the following line.
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    /**
     * Populate the database in the background.
     * If you want to start with more words, just add them.
     */
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final TemplateDao tDao;
        private final NoteDao nDao;

        PopulateDbAsync(AppDatabase db) {
            tDao = db.templateDao();
            nDao = db.noteDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.
            tDao.deleteAll();
            nDao.deleteAll();
            tDao.insertAll(Template.populateChromaticScale());
            tDao.insertAll(Template.populateMajorScales());
            tDao.insertAll(Template.populateMinorScales());
            tDao.insertAll(Template.populateBluesScales());
            nDao.insertAll(Note.populateData());
            return null;
        }
    }
}
