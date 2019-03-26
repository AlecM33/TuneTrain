package com.example.alec.tunetrain.Entities;


import com.example.alec.tunetrain.R;

import java.util.HashMap;

public class NoteFiles {

    private static NoteFiles INSTANCE;
    private HashMap<String, Integer> vals;

    private NoteFiles() {
        this.vals = new HashMap<>();
        this.populateHashMap();
    }

    public static NoteFiles getNoteFilesInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NoteFiles();
        }

        return INSTANCE;
    }

    private void populateHashMap() {
        this.vals.put("Ab", R.raw.ab);
        this.vals.put("A", R.raw.a);
        this.vals.put("A#", R.raw.bb);
        this.vals.put("Bb", R.raw.bb);
        this.vals.put("B", R.raw.b);
        this.vals.put("B#", R.raw.c);
        this.vals.put("Cb", R.raw.b);
        this.vals.put("C", R.raw.c);
        this.vals.put("C#", R.raw.cs);
        this.vals.put("Db", R.raw.cs);
        this.vals.put("D", R.raw.d);
        this.vals.put("D#", R.raw.eb);
        this.vals.put("Eb", R.raw.eb);
        this.vals.put("E", R.raw.e);
        this.vals.put("E#", R.raw.f);
        this.vals.put("Fb", R.raw.e);
        this.vals.put("F", R.raw.f);
        this.vals.put("F#", R.raw.fs);
        this.vals.put("Gb", R.raw.fs);
        this.vals.put("G", R.raw.g);
        this.vals.put("G#", R.raw.ab);
        this.vals.put("none", 0);
    }

    public HashMap<String, Integer> getMap() {
        return this.vals;
    }
}
