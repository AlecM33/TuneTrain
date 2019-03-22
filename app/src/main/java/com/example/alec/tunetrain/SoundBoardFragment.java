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

import com.example.alec.tunetrain.Daos.TemplateDao;
import com.example.alec.tunetrain.Entities.Note;
import com.example.alec.tunetrain.Entities.Template;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class SoundBoardFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "SoundBoardFragment";
    private List<Button> mPads;
    private HashMap<String, Integer> fileMap;
    private Template currentTemplate;
    private static final int NUMBER_OF_PADS = 12;
    private SoundPool mSoundPool;
    private int sounds[] = new int[NUMBER_OF_PADS];
    private String[] currentPads;
    private String lastPlayed = "A";
    private boolean buttonPressed = true;
    private Button mStartButton;


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
        View v = inflater.inflate(R.layout.fragment_training, container, false);
        AppDatabase db = AppDatabase.getAppDatabase(getActivity().getBaseContext());

        currentTemplate = db.templateDao().getTemplate("Chromatic");


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

        List<Note> Notes = db.noteDao().getNotes();
        fileMap = Note.getFileMap(Notes);
        currentPads = currentTemplate.getPads();

        //load soundIDs
        for (int i = 0; i < sounds.length; i++ ){
            sounds[i] = mSoundPool.load(getActivity().getBaseContext(), fileMap.get(currentPads[i]), 1);
        }


        mStartButton = v.findViewById(R.id.start);
        mStartButton.setOnClickListener(this);



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
    public void onClick(View v) {
        Log.d(TAG, lastPlayed);
        buttonPressed = true;
        switch (v.getId()){
            case R.id.start:
                mStartButton.setEnabled(false);
                Random r = new Random();
                int index = r.nextInt(currentPads.length);
                String randomNote = currentPads[index];

                mPads.get(index).performClick();
//                buttonPressed = false;
//
//                if (buttonPressed && randomNote.equals(lastPlayed)) {
//                    index = r.nextInt(currentPads.length);
//                    randomNote = currentPads[index];
//                    //sleep?
//                    mPads.get(index).performClick();
//                }

            case R.id.pad1:
                mSoundPool.play(sounds[0], 1, 1, 0, 0, 1);
                lastPlayed = mPads.get(0).getText().toString();
                break;

            case R.id.pad2:
                mSoundPool.play(sounds[1], 1, 1, 0, 0, 1);
                lastPlayed = mPads.get(1).getText().toString();
                break;

            case R.id.pad3:
                mSoundPool.play(sounds[2], 1, 1, 0, 0, 1);
                lastPlayed = mPads.get(2).getText().toString();
                break;

            case R.id.pad4:
                mSoundPool.play(sounds[3], 1, 1, 0, 0, 1);
                lastPlayed = mPads.get(3).getText().toString();
                break;

            case R.id.pad5:
                mSoundPool.play(sounds[4], 1, 1, 0, 0, 1);
                lastPlayed = mPads.get(4).getText().toString();

                break;

            case R.id.pad6:
                mSoundPool.play(sounds[5], 1, 1, 0, 0, 1);
                lastPlayed = mPads.get(5).getText().toString();

                break;

            case R.id.pad7:
                mSoundPool.play(sounds[6], 1, 1, 0, 0, 1);
                lastPlayed = mPads.get(6).getText().toString();

                break;

            case R.id.pad8:
                mSoundPool.play(sounds[7], 1, 1, 0, 0, 1);
                lastPlayed = mPads.get(7).getText().toString();

                break;

            case R.id.pad9:
                mSoundPool.play(sounds[8], 1, 1, 0, 0, 1);
                lastPlayed = mPads.get(8).getText().toString();

                break;

            case R.id.pad10:
                mSoundPool.play(sounds[9], 1, 1, 0, 0, 1);
                lastPlayed = mPads.get(9).getText().toString();

                break;

            case R.id.pad11:
                mSoundPool.play(sounds[10], 1, 1, 0, 0, 1);
                lastPlayed = mPads.get(10).getText().toString();

                break;

            case R.id.pad12:
                mSoundPool.play(sounds[11], 1, 1, 0, 0, 2);
                lastPlayed = mPads.get(11).getText().toString();

                break;
        }

    }
}
