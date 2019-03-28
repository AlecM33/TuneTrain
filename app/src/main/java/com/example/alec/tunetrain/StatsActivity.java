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
import com.example.alec.tunetrain.Entities.Template;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class StatsActivity extends AppCompatActivity {

    private static final String TAG = "StatsActivity";
    private List<Guess> allGuesses;
    public TextView mCorrectPercentage;
    public AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");

        setContentView(R.layout.activity_stats);
        db = AppDatabase.getAppDatabase(this.getBaseContext());
        StatsActivity.GetStatTask statTask = new StatsActivity.GetStatTask();
        mCorrectPercentage = (TextView)findViewById(R.id.percent_correct);
        try {
            allGuesses = statTask.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (allGuesses.size() > 0) {
            double correctPercentage = calculateCorrectPercentage(allGuesses);
            mCorrectPercentage.setText(String.format(Locale.getDefault(), "%.2f%%", correctPercentage));
        } else {
            mCorrectPercentage.setText(R.string.correct_percentage_no_guesses);
        }
    }

    private class GetStatTask extends AsyncTask<Void, Void, List<Guess>> {
        Template currentTemplate;

        @Override
        protected List<Guess> doInBackground(Void... params) {
            return db.guessDao().getGuesses();
        }

        protected void onProgressUpdate() {
        }

        protected void onPostExecute() {
        }
    }


    private static double calculateCorrectPercentage(List<Guess> allGuesses) {
        double correct = 0;
        double incorrect = 0;
        for (Guess guess : allGuesses) {
            if(guess.correct) {
                correct ++;
            } else {
                incorrect++;
            }
        }
        return correct / (correct + incorrect) * 100;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }
    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }
    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }
}
