package com.example.alec.tunetrain;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class TrainingActivity extends AppCompatActivity implements TrainingModeDialogFragment.TrainingDialogListener {

    private static final String TAG = "TrainingActivity";
    private String trainingMode = "";
    private String authToken = "";
    private SpotifySession mSpotify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);

        //display dialog for selection training mode
        showNoticeDialog();

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
        mSpotify = SpotifySession.getSpotifyInstance(this);

    }
    @Override
    public void onResume() {
        super.onResume();

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


    public void showNoticeDialog() {
        // Create an instance of the dialog fragment and show it
        DialogFragment dialog = new TrainingModeDialogFragment();
        dialog.show(this.getSupportFragmentManager().beginTransaction(), "TrainingModeDialogFragment");
    }

    // The dialog fragment receives a reference to this Activity through the
    // Fragment.onAttach() callback, which it uses to call the following methods
    // defined by the TrainingDialogFragment.TrainingDialogListener interface
    @Override
    public void onDialogSpotifyClick(DialogFragment dialog) {
        // User touched the dialog's positive button
        this.trainingMode = "Spotify";
        Log.d(TAG, "TRAINING MODE: " + trainingMode);
        loadSoundBoardFragment(trainingMode);

    }

    @Override
    public void onDialogNoteClick(DialogFragment dialog) {
        // User touched the dialog's negative button
        this.trainingMode = "Note";
        Log.d(TAG, "TRAINING MODE: " + trainingMode);
        loadSoundBoardFragment(trainingMode);


    }

    public void loadSoundBoardFragment (String mode) {
        //calling and displaying the stats Fragment
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragment = new SoundBoardFragment();
            Bundle bundle = new Bundle();
            bundle.putString("Mode", trainingMode);
            fragment.setArguments(bundle);
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        mSpotify.setResponse(requestCode, resultCode, intent);

    }
}
