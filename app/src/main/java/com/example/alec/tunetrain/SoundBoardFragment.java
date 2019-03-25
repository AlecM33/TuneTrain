package com.example.alec.tunetrain;

import android.media.AudioManager;
import android.media.SoundPool;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class SoundBoardFragment extends Fragment implements View.OnClickListener  {

    private static final String TAG = "SoundBoardFragment";
    private List<Button> mPads;
    private HashMap<String, Integer> fileMap;
    private Template currentTemplate;
    private static final int NUMBER_OF_PADS = 12;
    private SoundPool mSoundPool;
    private int sounds[] = new int[NUMBER_OF_PADS];
    private String[] currentPads;
    private String lastPlayed = "A";
    private Button mPlayButton;
    private Button mNextButton;
    private Random r = new Random();
    private int rIndex =  0;
    private String randomNote = "";
    private Toast toast;
    private String trainingMode;
    private boolean buttonPressed;

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
        trainingMode = this.getArguments().getString("Mode");
        View v = inflater.inflate(R.layout.fragment_training, container, false);
        AppDatabase db = AppDatabase.getAppDatabase(getActivity().getBaseContext());
        db.templateDao().insertAll(Template.populateChromaticScale());
        db.templateDao().insertAll(Template.populateMajorScales());
        db.templateDao().insertAll(Template.populateMinorScales());
        db.templateDao().insertAll(Template.populateBluesScales());
        db.noteDao().insertAll(Note.populateData());

        currentTemplate = db.templateDao().getTemplate("Chromatic");
        List<Template> allTemplates = db.templateDao().getTemplates();
        List<Note> Notes = db.noteDao().getNotes();
        for (Template template : allTemplates) {
            Log.d(template.templateName, template.pad1);
            Log.d(template.templateName, template.pad2);
            Log.d(template.templateName, template.pad3);
            Log.d(template.templateName, template.pad4);
            Log.d(template.templateName, template.pad5);
            Log.d(template.templateName, template.pad6);
            Log.d(template.templateName, template.pad7);
            Log.d(template.templateName, template.pad8);
            Log.d(template.templateName, template.pad9);
            Log.d(template.templateName, template.pad10);
            Log.d(template.templateName, template.pad11);
            Log.d(template.templateName, template.pad12);

        }

        //set up onclick listeners for buttons
        mPads = new ArrayList<>();
        for(int id : BUTTON_IDS) {
            Button button = v.findViewById(id);
            button.setOnClickListener(this); // maybe
            mPads.add(button);
        }

        //initialize soundpool
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mSoundPool = new SoundPool.Builder().setMaxStreams(5).build();
        } else {
            mSoundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        }

        fileMap = Note.getFileMap(Notes);
        currentPads = currentTemplate.getPads();

        //load soundIDs
        for (int i = 0; i < sounds.length; i++ ){
            sounds[i] = mSoundPool.load(getActivity().getBaseContext(), fileMap.get(currentPads[i]), 1);
        }


        mPlayButton = v.findViewById(R.id.play);
        mPlayButton.setOnClickListener(this);

        mNextButton = v.findViewById(R.id.next);
        mNextButton.setOnClickListener(this);

        toast = Toast.makeText(getActivity(), "CORRECT", Toast.LENGTH_SHORT);



        // DEBUG STUFF ----------------------------------------------

//        Log.d(TAG, "CURRENT TEMP");
//        for (int i = 0; i < currentTemplate.getPads().length; i++) {
//            Log.d(TAG, currentTemplate.getPads()[i]);
//        }
//
//        Log.d(TAG, "NOTES LIST");
//        for (int i = 0; i < Notes.size(); i++) {
//            Log.d(TAG, Notes.get(i).noteName);
//        }
//
//        Log.d(TAG, "MAP");
//        for (Map.Entry<String, Integer> entry : fileMap.entrySet()) {
//            String key = entry.getKey();
//            Integer value = entry.getValue();
//            Log.d(TAG, key);
//            Log.d(TAG, value.toString());
//        }
//
//        Log.d(TAG, "CURRENT PADS LIST");
//        for (int i = 0; i < currentPads.length; i++) {
//            Log.d(TAG, currentPads[i]);
//        }


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
                lastPlayed = mPads.get(0).getText().toString();
                checkIfCorrectNote();
                break;

            case R.id.pad2:
                mSoundPool.play(sounds[1], 1, 1, 0, 0, 1);
                lastPlayed = mPads.get(1).getText().toString();
                checkIfCorrectNote();
                break;

            case R.id.pad3:
                mSoundPool.play(sounds[2], 1, 1, 0, 0, 1);
                lastPlayed = mPads.get(2).getText().toString();
                checkIfCorrectNote();
                break;

            case R.id.pad4:
                mSoundPool.play(sounds[3], 1, 1, 0, 0, 1);
                lastPlayed = mPads.get(3).getText().toString();
                checkIfCorrectNote();
                break;

            case R.id.pad5:
                mSoundPool.play(sounds[4], 1, 1, 0, 0, 1);
                lastPlayed = mPads.get(4).getText().toString();
                checkIfCorrectNote();
                break;

            case R.id.pad6:
                mSoundPool.play(sounds[5], 1, 1, 0, 0, 1);
                lastPlayed = mPads.get(5).getText().toString();
                checkIfCorrectNote();
                break;

            case R.id.pad7:
                mSoundPool.play(sounds[6], 1, 1, 0, 0, 1);
                lastPlayed = mPads.get(6).getText().toString();
                checkIfCorrectNote();
                break;

            case R.id.pad8:
                mSoundPool.play(sounds[7], 1, 1, 0, 0, 1);
                lastPlayed = mPads.get(7).getText().toString();
                checkIfCorrectNote();

                break;

            case R.id.pad9:
                mSoundPool.play(sounds[8], 1, 1, 0, 0, 1);
                lastPlayed = mPads.get(8).getText().toString();
                checkIfCorrectNote();

                break;

            case R.id.pad10:
                mSoundPool.play(sounds[9], 1, 1, 0, 0, 1);
                lastPlayed = mPads.get(9).getText().toString();
                checkIfCorrectNote();

                break;

            case R.id.pad11:
                mSoundPool.play(sounds[10], 1, 1, 0, 0, 1);
                lastPlayed = mPads.get(10).getText().toString();
                checkIfCorrectNote();

                break;

            case R.id.pad12:
                mSoundPool.play(sounds[11], 1, 1, 0, 0, 2);
                lastPlayed = mPads.get(11).getText().toString();
                checkIfCorrectNote();

                break;
        }

    }

    public void playNextNote() {
        rIndex = r.nextInt(currentPads.length);
        randomNote = currentPads[rIndex];
        mSoundPool.play(sounds[rIndex], 1, 1, 0, 0, 1);

    }

    public void checkIfCorrectNote() {

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
