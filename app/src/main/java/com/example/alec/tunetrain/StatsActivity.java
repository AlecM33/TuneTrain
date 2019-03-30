package com.example.alec.tunetrain;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.alec.tunetrain.Entities.Guess;
import com.example.alec.tunetrain.Entities.PlayedNote;
import com.example.alec.tunetrain.Entities.Template;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class StatsActivity extends AppCompatActivity {

    private static final String TAG = "StatsActivity";
    private List<Guess> allGuesses;
    private List<PlayedNote> allNotes;
    public TextView mCorrectPercentage;
    public TextView mGuessesNeeded;
    public TextView mFavoriteNote;
    public TextView mNotesPlayed;
    public AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");

        setContentView(R.layout.activity_stats);
        db = AppDatabase.getAppDatabase(this.getBaseContext());
        StatsActivity.GetGuessStatsTask guessTask = new StatsActivity.GetGuessStatsTask();
        //StatsActivity.GetNotesPlayedStatsTask noteTask = new StatsActivity.GetNotesPlayedStatsTask();
        mCorrectPercentage = (TextView)findViewById(R.id.percent_correct);
        mGuessesNeeded = (TextView)findViewById(R.id.guesses_needed);
        mFavoriteNote = (TextView)findViewById(R.id.favorite_note);
        mNotesPlayed = (TextView)findViewById(R.id.notes_played);
        // TODO: Refactor
        try {
            allGuesses = guessTask.execute().get();
            allNotes = db.notesPlayedDao().getPlayedNotes();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        calculateGuessStats();
        calculateNoteStats();
    }

    private void calculateGuessStats() {
        if (allGuesses.size() > 0) {
            double averageGuessesNeeded = calculateAverageGuessedNeeded(allGuesses);
            double correctPercentage = (1 / averageGuessesNeeded) * 100;
            mCorrectPercentage.setText(String.format(Locale.getDefault(), "%.2f%%", correctPercentage));
            mGuessesNeeded.setText(String.format(Locale.getDefault(), "%.2f", averageGuessesNeeded));
        } else {
            mCorrectPercentage.setText(R.string.no_guesses);
            mGuessesNeeded.setText(R.string.no_guesses);
        }
    }

    private void calculateNoteStats() {
        if (allNotes.size() > 0) {
            mNotesPlayed.setText(String.format(Locale.getDefault(), "%d", allNotes.size()));
            mFavoriteNote.setText(String.format(Locale.getDefault(), "%s", calculateFavoriteNote(allNotes)));
        } else {
            mNotesPlayed.setText(R.string.no_guesses);
            mFavoriteNote.setText(R.string.no_guesses);
        }
    }

    // TODO: Refactor
    private class GetGuessStatsTask extends AsyncTask<Void, Void, List<Guess>> {

        @Override
        protected List<Guess> doInBackground(Void... params) {
            return db.guessDao().getGuesses();
        }

        protected void onProgressUpdate() {
        }

        protected void onPostExecute() {
        }
    }

    // TODO: Refactor
    private class GetNotesPlayedStatsTask extends AsyncTask<Void, Void, List<PlayedNote>> {

        @Override
        protected List<PlayedNote> doInBackground(Void... params) {
            return db.notesPlayedDao().getPlayedNotes();
        }

        protected void onProgressUpdate() {
        }

        protected void onPostExecute() {
        }
    }

    private static double calculateAverageGuessedNeeded(List<Guess> allGuesses) {
        int current = 0;
        List<Integer> guessesNeeded = new ArrayList<>();
        while (current < allGuesses.size()) {
            int incorrectStreak = 0;
            while(current < allGuesses.size() && !allGuesses.get(current).correct) {
                incorrectStreak++;
                current++;
            }
            current++;
            guessesNeeded.add(incorrectStreak + 1);
        }
        int total = 0;
        for (int streak : guessesNeeded) {
            total += streak;
        }
        return (double)total / guessesNeeded.size();
    }

    //TODO: implement using notesPlayedDao
    private static String calculateFavoriteNote(List<PlayedNote> allNotes) {
        String favoriteName = "";
        int max = 0;
        Map<String, Integer> frequencies = new HashMap<>();
        for (PlayedNote note : allNotes) {
            if(frequencies.containsKey(note.noteName)) {
                int occurrences = frequencies.get(note.noteName);
                frequencies.put(note.noteName, occurrences + 1);
                if (occurrences + 1 > max) {
                    favoriteName = note.noteName;
                    max = occurrences + 1;
                }
            } else {
                frequencies.put(note.noteName, 0);
            }
        }
        return favoriteName;
    }
}
