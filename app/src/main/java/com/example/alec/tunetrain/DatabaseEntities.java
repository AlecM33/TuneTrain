package com.example.alec.tunetrain;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

public class DatabaseEntities {

    /* TEMPLATES */
    @Entity(tableName = "Templates")
    public class Template {
        @PrimaryKey(autoGenerate = true)
        public int templateId;

        @ColumnInfo(name = "template_name")
        private String templateName;

        @ColumnInfo(name = "pad1")
        private int pad1;

        @ColumnInfo(name = "pad2")
        private int pad2;

        @ColumnInfo(name = "pad3")
        private int pad3;

        @ColumnInfo(name = "pad4")
        private int pad4;

        @ColumnInfo(name = "pad5")
        private int pad5;

        @ColumnInfo(name = "pad6")
        private int pad6;

        @ColumnInfo(name = "pad7")
        private int pad7;

        @ColumnInfo(name = "pad8")
        private int pad8;

        @ColumnInfo(name = "pad9")
        private int pad9;

        @ColumnInfo(name = "pad10")
        private int pad10;

        @ColumnInfo(name = "pad11")
        private int pad11;

        @ColumnInfo(name = "pad12")
        private int pad12;

        public Template(String name, int pad1, int pad2, int pad3, int pad4, int pad5, int pad6,
                        int pad7, int pad8, int pad9, int pad10, int pad11, int pad12){
            this.templateName = name;
            this.pad1 = pad1;
            this.pad2 = pad2;
            this.pad3 = pad3;
            this.pad4 = pad4;
            this.pad5 = pad5;
            this.pad6 = pad6;
            this.pad7 = pad7;
            this.pad8 = pad8;
            this.pad9 = pad9;
            this.pad10 = pad10;
            this.pad11 = pad11;
            this.pad12 = pad12;
        }
    }

    /* NOTES */
    @Entity(tableName = "Notes")
    public class Note {
        @PrimaryKey(autoGenerate = true)
        public int noteId;

        @ColumnInfo(name = "note_name")
        private String noteName;

        @ColumnInfo(name = "note_freq")
        private int noteFreq;

        public Note(String noteName, int noteFreq) {
            this.noteName = noteName;
            this.noteFreq = noteFreq;
        }
    }

    /* PLAYLISTS */
    @Entity(tableName = "Playlists")
    public class Playlist {
        @PrimaryKey(autoGenerate = true)
        public int playlistId;

        @ColumnInfo(name = "playlist_name")
        private String playlistName;

        @ColumnInfo(name = "spotify_uri")
        private String spotifyUri;

        public Playlist(String name, String uri) {
            this.playlistName = name;
            this.spotifyUri = uri;
        }
    }

    /* SCALES */
    @Entity(tableName = "Scales")
    public class Scale {
        @PrimaryKey
        public int scaleId;

        @ColumnInfo(name = "scale_type")
        private String scaleType;

        @ColumnInfo(name = "intervals")
        private String intervals;

        public Scale(String type, String intervals) {
            this.scaleType = type;
            this.intervals = intervals;
        }
    }

    /* GUESSES */
    @Entity(tableName = "Guesses")
    public class Guess {
        @PrimaryKey(autoGenerate = true)
        public int guessId;

        @ColumnInfo(name = "game_mode")
        private String gameMode;

        @ColumnInfo(name = "correct")
        private boolean correct;

        @ColumnInfo(name = "start_of_session")
        private boolean startOfSession;

        public Guess(String mode, boolean correct, boolean startOfSession) {
            this.gameMode = mode;
            this.correct = correct;
            this.startOfSession = startOfSession;
        }
    }

    /* NOTES PLAYED */
    @Entity(tableName = "Notes_Played")
    public class PlayedNote {
        @PrimaryKey(autoGenerate = true)
        int playId;

        @ColumnInfo(name = "note_id")
        private int noteId;

        public PlayedNote(int id) {
            this.noteId = id;
        }
    }

    /* SPOTIFY_ACCOUNTS */
    @Entity(tableName = "Spotify_Accounts")
    public class SpotifyAccount {
        @PrimaryKey(autoGenerate = true)
        int accountId;

        @ColumnInfo(name = "username")
        private String username;

        @ColumnInfo(name = "password")
        private String password;

        @ColumnInfo(name = "is_linked")
        private boolean isLinked;

        public SpotifyAccount(String username, String password, boolean isLinked) {
            this.username = username;
            this.password = password;
            this.isLinked = isLinked;
        }
    }
}
