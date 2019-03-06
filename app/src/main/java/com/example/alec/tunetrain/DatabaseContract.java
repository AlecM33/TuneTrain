package com.example.alec.tunetrain;

import android.provider.BaseColumns;

public class DatabaseContract {

    private DatabaseContract() {}

    public static class Templates implements BaseColumns {
        public static final String TABLE_NAME = "Templates";
        public static final String COLUMN_TEMPLATE_ID = "template_id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PAD1 = "pad1";
        public static final String COLUMN_PAD2 = "pad2";
        public static final String COLUMN_PAD3 = "pad3";
        public static final String COLUMN_PAD4 = "pad4";
        public static final String COLUMN_PAD5 = "pad5";
        public static final String COLUMN_PAD6 = "pad6";
        public static final String COLUMN_PAD7 = "pad7";
        public static final String COLUMN_PAD8 = "pad8";
        public static final String COLUMN_PAD9 = "pad9";
        public static final String COLUMN_PAD10 = "pad10";
        public static final String COLUMN_PAD11 = "pad11";
        public static final String COLUMN_PAD12 = "pad12";
    }

    public static class Notes implements BaseColumns {
        public static final String TABLE_NAME = "Notes";
        public static final String COLUMN_NOTE_ID = "note_id";
        public static final String COLUMN_NOTE_FREQ = "note_freq";
        public static final String COLUMN_NOTE_NAME = "note_name";
    }

    public static class Playlists implements BaseColumns {
        public static final String TABLE_NAME = "Playlists";
        public static final String COLUMN_PLAYLIST_ID = "playlist_id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_SPOTIFY_URI = "spotify_uri";
    }

    public static class Scales implements BaseColumns {
        public static final String TABLE_NAME = "Scales";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_INTERVALS = "intervals";
    }

    public static class Guesses implements BaseColumns {
        public static final String TABLE_NAME = "Guesses";
        public static final String COLUMN_GUESS_ID = "guess_id";
        public static final String COLUMN_MODE = "mode";
        public static final String COLUMN_CORRECT = "correct";
        public static final String COLUMN_START_OF_SESSION = "start_of_session";
    }

    public static class NotesPlayed implements BaseColumns {
        public static final String TABLE_NAME = "Notes_Played";
        public static final String COLUMN_PLAY_ID = "play_id";
        public static final String COLUMN_NOTE_ID = "note_id";
    }

    public static class SpotifyAccounts implements BaseColumns {
        public static final String TABLE_NAME = "Spotify_Accounts";
        public static final String COLUMN_ACCOUNT_ID = "account_id";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_PASSWORD = "password";
        public static final String COLUMN_IS_LINKED = "is_linked";
    }
}
