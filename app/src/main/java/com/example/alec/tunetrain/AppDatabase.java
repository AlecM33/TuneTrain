package com.example.alec.tunetrain;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.*;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.alec.tunetrain.Daos.*;
import com.example.alec.tunetrain.Entities.*;

import com.example.alec.tunetrain.Daos.TemplateDao;

@Database(entities = {Template.class, Guess.class, Note.class, PlayedNote.class},
        version = 3, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {


    public abstract TemplateDao templateDao();
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
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }

        //begin and end transaction to ensure db properly prepopulates
        INSTANCE.beginTransaction();
        INSTANCE.endTransaction();
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
        public void onCreate (SupportSQLiteDatabase db) {
            new PopulateDbAsync(INSTANCE).execute();
            super.onCreate(db);
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    /**
     * Populate the database in the background.
     * If you want to start with more notes or templates, just add them.
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
            tDao.deleteAll();
            nDao.deleteAll();
            tDao.insertAll(Template.populateChromaticScale());
            tDao.insertAll(Template.populateMajorScales());
            tDao.insertAll(Template.populateNaturalMinorScales());
            tDao.insertAll(Template.populateHarmonicMinorScales());
            tDao.insertAll(Template.populateBluesScales());
            nDao.insertAll(Note.populateData());
            return null;
        }
    }
}
