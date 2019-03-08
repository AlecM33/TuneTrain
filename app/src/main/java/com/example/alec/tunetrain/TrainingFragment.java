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
import com.example.alec.tunetrain.Entities.Note;
import com.example.alec.tunetrain.Entities.Template;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TrainingFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "StatsFragment";
    private List<Button> mPads;
    private HashMap<String, Integer> fileMap;
    private Template currentTemplate;
    private static final int NUMBER_OF_PADS = 12;
    private SoundPool mSoundPool;
    private int sounds[] = new int[NUMBER_OF_PADS];
    private String[] currentPads;

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

    private static final Integer[] FILE_IDS = {
            R.raw.a,
            R.raw.bb,
            R.raw.b,
            R.raw.c,
            R.raw.cs,
            R.raw.d,
            R.raw.eb,
            R.raw.e,
            R.raw.f,
            R.raw.fs,
            R.raw.g,
            R.raw.ab,
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

//        currentTemplate = AppDatabase.getAppDatabase(getActivity().getBaseContext())
//                .templateDao().getTemplate("Chromatic");
//
//        List<Note> Notes = AppDatabase.getAppDatabase(getActivity().getBaseContext()).noteDao().getNotes();
//        fileMap = Note.getFileMap(Notes);
//        currentPads = currentTemplate.getPads();



        //load soundIDs
        for (int i = 0; i < sounds.length; i++ ){
//            sounds[i] = mSoundPool.load(getActivity().getBaseContext(), fileMap.get(currentPads[i]), 1);
            sounds[i] = mSoundPool.load(getActivity().getBaseContext(), FILE_IDS[i], 1);
        }

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pad1:
                mSoundPool.play(sounds[0], 1, 1, 0, 0, 1);
                break;

            case R.id.pad2:
                mSoundPool.play(sounds[1], 1, 1, 0, 0, 1);
                break;

            case R.id.pad3:
                mSoundPool.play(sounds[2], 1, 1, 0, 0, 1);
                break;

            case R.id.pad4:
                mSoundPool.play(sounds[3], 1, 1, 0, 0, 1);
                break;

            case R.id.pad5:
                mSoundPool.play(sounds[4], 1, 1, 0, 0, 1);
                break;

            case R.id.pad6:
                mSoundPool.play(sounds[5], 1, 1, 0, 0, 1);
                break;

            case R.id.pad7:
                mSoundPool.play(sounds[6], 1, 1, 0, 0, 1);
                break;

            case R.id.pad8:
                mSoundPool.play(sounds[7], 1, 1, 0, 0, 1);
                break;

            case R.id.pad9:
                mSoundPool.play(sounds[8], 1, 1, 0, 0, 1);
                break;

            case R.id.pad10:
                mSoundPool.play(sounds[9], 1, 1, 0, 0, 1);
                break;

            case R.id.pad11:
                mSoundPool.play(sounds[10], 1, 1, 0, 0, 1);
                break;

            case R.id.pad12:
                mSoundPool.play(sounds[11], 1, 1, 0, 0, 2);
                break;
        }

    }
}
