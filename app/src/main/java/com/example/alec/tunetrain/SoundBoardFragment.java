package com.example.alec.tunetrain;

import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.example.alec.tunetrain.Entities.Note;
import com.example.alec.tunetrain.Entities.Template;

import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class SoundBoardFragment extends Fragment implements View.OnClickListener  {

    private static final String TAG = "SoundBoardFragment";
    private List<Button> mPads;
    private HashMap<String, Integer> fileMap;
    private Template currentTemplate;
    private static final int NUMBER_OF_PADS = 12;
    private SoundPool mSoundPool;
    private int sounds[] = new int[NUMBER_OF_PADS];
    private Note[] currentPads;
    private String lastPlayed = "A";
    List<Template> allTemplates;
    private Button mPlayButton;
    private Button mNextButton;
    private Button mSelectButton;
    private Button mCreateButton;
    private Random r = new Random();
    private int rIndex =  0;
    private String randomNote = "";
    private Toast toast;
    private String trainingMode;
    private boolean buttonPressed;
    private View v;
    private AppDatabase db;

    private SpotifySession mSpotify;
    private static final int[] BUTTON_IDS = {
            R.id.pad1,
            R.id.pad2,
            R.id.pad3,
            R.id.pad4,
            R.id.pad5,
            R.id.pad6,
            R.id.pad7,
            R.id.pad8,
            R.id.pad9,
            R.id.pad10,
            R.id.pad11,
            R.id.pad12,
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate(Bundle) called");
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView(Inflater) called");

        //initialize db
        this.db = AppDatabase.getAppDatabase(getActivity().getBaseContext());

        this.trainingMode = this.getArguments().getString("Mode");
        if (trainingMode.equals("Sandbox")) {
            startSandboxMode(inflater, container);
        } else {
            startTrainingMode(inflater, container);
        }

        setUpSoundPool();

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        mSpotify = SpotifySession.getSpotifyInstance(getActivity());
        mSpotify.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        mSpotify.disconnect();
    }

    @Override
    public void onClick(View v) {
//        Log.d(TAG, lastPlayed);
        buttonPressed = true;
        switch (v.getId()){
            case R.id.select:
                Intent intent = new Intent(getActivity(), SelectTemplateActivity.class);
                startActivity(intent);
                break;
            case R.id.play:
                mPlayButton.setEnabled(false);
                playNextNote();
                 break;
            case R.id.next:
                mNextButton.setEnabled(false);
                playNextNote();
                break;
            case R.id.pad1:
                mSoundPool.play(sounds[0], 1, 1, 0, 0, 1);
                lastPlayed = currentPads[0].noteName;
                handleNotePress();
                break;

            case R.id.pad2:
                mSoundPool.play(sounds[1], 1, 1, 0, 0, 1);
                lastPlayed = currentPads[1].noteName;
                handleNotePress();
                break;

            case R.id.pad3:
                mSoundPool.play(sounds[2], 1, 1, 0, 0, 1);
                lastPlayed = currentPads[2].noteName;
                handleNotePress();
                break;

            case R.id.pad4:
                mSoundPool.play(sounds[3], 1, 1, 0, 0, 1);
                lastPlayed = currentPads[3].noteName;
                handleNotePress();
                break;

            case R.id.pad5:
                mSoundPool.play(sounds[4], 1, 1, 0, 0, 1);
                lastPlayed = currentPads[4].noteName;
                handleNotePress();
                break;

            case R.id.pad6:
                mSoundPool.play(sounds[5], 1, 1, 0, 0, 1);
                lastPlayed = currentPads[5].noteName;
                handleNotePress();
                break;

            case R.id.pad7:
                mSoundPool.play(sounds[6], 1, 1, 0, 0, 1);
                lastPlayed = currentPads[6].noteName;
                handleNotePress();
                break;

            case R.id.pad8:
                mSoundPool.play(sounds[7], 1, 1, 0, 0, 1);
                lastPlayed = currentPads[7].noteName;
                handleNotePress();

                break;

            case R.id.pad9:
                mSoundPool.play(sounds[8], 1, 1, 0, 0, 1);
                lastPlayed = currentPads[8].noteName;
                handleNotePress();

                break;

            case R.id.pad10:
                mSoundPool.play(sounds[9], 1, 1, 0, 0, 1);
                lastPlayed = currentPads[9].noteName;
                handleNotePress();

                break;

            case R.id.pad11:
                mSoundPool.play(sounds[10], 1, 1, 0, 0, 1);
                lastPlayed = currentPads[10].noteName;
                handleNotePress();

                break;

            case R.id.pad12:
                mSoundPool.play(sounds[11], 1, 1, 0, 0, 2);
                lastPlayed = currentPads[11].noteName;
                handleNotePress();

                break;
        }

    }

    private void startSandboxMode(LayoutInflater inflater, ViewGroup container) {
        v = inflater.inflate(R.layout.fragment_sandbox, container, false);
        mSelectButton = v.findViewById(R.id.select);
        mSelectButton.setOnClickListener(this);
        mCreateButton = v.findViewById(R.id.create);
        mCreateButton.setOnClickListener(this);

        GetCurrentTemplateTask templateTask = new GetCurrentTemplateTask();
        try {
            currentPads = templateTask.execute().get().getPads();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        setOnClickListeners();
    }

    private class GetCurrentTemplateTask extends AsyncTask<Void, Void, Template> {
        Template currentTemplate;

        @Override
        protected Template doInBackground(Void... params) {
           return db.templateDao().getTemplate("C Major");
        }

        protected void onProgressUpdate() {
        }

        protected void onPostExecute() {
        }
    }

    private void startTrainingMode(LayoutInflater inflater, ViewGroup container) {
        v = inflater.inflate(R.layout.fragment_training, container, false);
        mPlayButton = v.findViewById(R.id.play);
        mPlayButton.setOnClickListener(this);
        mNextButton = v.findViewById(R.id.next);
        mNextButton.setOnClickListener(this);
        toast = Toast.makeText(getActivity(), "CORRECT", Toast.LENGTH_SHORT);

        currentTemplate = db.templateDao().getTemplate("Chromatic");
        GetCurrentTemplateTask templateTask = new GetCurrentTemplateTask();
        try {
            currentPads = templateTask.execute().get().getPads();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        setOnClickListeners();


        if (this.trainingMode.equals("Spotify")) {
            startSpotifyMode();
        } else {
            startNoteMode();
        }
    }

    private void startNoteMode() {

    }

    private void startSpotifyMode() {

    }

    private void setUpSoundPool() {
        //initialize soundpool
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mSoundPool = new SoundPool.Builder().setMaxStreams(5).build();
        } else {
            mSoundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        }


        //load soundIDs
        for (int i = 0; i < sounds.length; i++ ){
            if (!currentPads[i].noteName.equals("none")) {
                sounds[i] = mSoundPool.load(getActivity().getBaseContext(), currentPads[i].fileName, 1);
            }
        }
    }

    private void setOnClickListeners() {
        //set up onclick listeners for buttons
        for(int i = 0; i <BUTTON_IDS.length; i++) {
            Button button = v.findViewById(BUTTON_IDS[i]);
            if (currentPads[i].noteName.equals("none")) {
                button.setVisibility(View.INVISIBLE);
            } else {
                button.setOnClickListener(this); // maybe
                button.setText(currentPads[i].noteName);
            }

        }
    }
    public void playNextNote() {
        rIndex = r.nextInt(currentPads.length);
        randomNote = currentPads[rIndex].noteName;
        mSoundPool.play(sounds[rIndex], 1, 1, 0, 0, 1);

    }

    public void handleNotePress() {
        if (trainingMode.equals("Note") || trainingMode.equals("Spotify")) {
            if (lastPlayed.equals(randomNote)) {
                Log.d(TAG, "CORRECT");
                toast.setText("CORRECT");
                toast.show();
                mPlayButton.setEnabled(true);
            } else {
                toast.setText("INCORRECT, TRY AGAIN");
                toast.show();
                Log.d(TAG, "INCORRECT");
            }
        }

    }

}
