package com.example.alec.tunetrain;

import android.provider.ContactsContract;

public class DatabaseManipulation {

    /* TEMPLATES*/
    private static final String SQL_CREATE_TEMPLATES =
            "CREATE TABLE " + DatabaseContract.Templates.TABLE_NAME + " (" +
                    DatabaseContract.Templates.COLUMN_TEMPLATE_ID + " INTEGER PRIMARY KEY," +
                    DatabaseContract.Templates.COLUMN_NAME + " VARCHAR(255)," +
                    DatabaseContract.Templates.COLUMN_PAD1 + " INT," +
                    DatabaseContract.Templates.COLUMN_PAD2 + " INT," +
                    DatabaseContract.Templates.COLUMN_PAD3 + " INT," +
                    DatabaseContract.Templates.COLUMN_PAD4 + " INT," +
                    DatabaseContract.Templates.COLUMN_PAD5 + " INT," +
                    DatabaseContract.Templates.COLUMN_PAD6 + " INT," +
                    DatabaseContract.Templates.COLUMN_PAD7 + " INT," +
                    DatabaseContract.Templates.COLUMN_PAD8 + " INT," +
                    DatabaseContract.Templates.COLUMN_PAD9 + " INT," +
                    DatabaseContract.Templates.COLUMN_PAD10 + " INT," +
                    DatabaseContract.Templates.COLUMN_PAD11 + " INT," +
                    DatabaseContract.Templates.COLUMN_PAD12 + " INT)";

    private static final String SQL_DELETE_TEMPLATES =
            "DROP TABLE IF EXISTS " + DatabaseContract.Templates.TABLE_NAME;

    /* NOTES */

    private static final String SQL_CREATE_NOTES =
            "CREATE TABLE " + DatabaseContract.Notes.TABLE_NAME + " (" +
                    DatabaseContract.Notes.COLUMN_NOTE_ID + " INT PRIMARY KEY," +
                    DatabaseContract.Notes.COLUMN_NOTE_NAME + " VARCHAR(255)," +
                    DatabaseContract.Notes.COLUMN_NOTE_FREQ + " INT)";


    private static final String SQL_DELETE_NOTES =
            "DROP TABLE IF EXISTS " + DatabaseContract.Notes.TABLE_NAME;


}
