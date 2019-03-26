package com.example.alec.tunetrain;

import android.arch.persistence.room.TypeConverter;

import com.example.alec.tunetrain.Entities.Note;
import com.example.alec.tunetrain.Entities.NoteFiles;

import java.util.HashMap;

public class Converters {

    @TypeConverter
    public static Note fromName(String value) {
        NoteFiles n = NoteFiles.getNoteFilesInstance();
        HashMap<String, Integer> map = n.getMap();
        return new Note(value, map.get(value));
    }

    @TypeConverter
    public static String ToName(Note note) {
        return note.noteName;
    }
}
