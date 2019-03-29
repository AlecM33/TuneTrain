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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.alec.tunetrain.Entities.Guess;
import com.example.alec.tunetrain.Entities.Note;
import com.example.alec.tunetrain.Entities.Template;

import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import kaaes.spotify.webapi.android.models.PlaylistSimple;

public class SoundBoardFragment extends Fragment implements View.OnClickListener  {

    private static final String TAG = "SoundBoardFragment";
    private HashMap<String, Integer> fileMap;
    private static final int NUMBER_OF_PADS = 12;
    private SoundPool mSoundPool;
    private int sounds[] = new int[NUMBER_OF_PADS];
    private Note[] currentPads;
    private String lastPlayed = "A";
    private Button mPlayButton;
    private Button mNextButton;
    private boolean startOfSession;
    private Button mSelectButton;
    private Button mCreateButton;
    private Button mPlaylistButton;
    private Button mPrevButton;
    private String templateName;
    private Random r = new Random();
    private int rIndex =  0;
    private String randomNote = "";
    private Toast toast;
    private String trainingMode;
    private View v;
    private AppDatabase db;
    private boolean GameStart = false;
    private boolean isPlaying;

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
        this.templateName = this.getArguments().getString("templateName");
        if (this.templateName != null) {
            Log.d("new name", this.templateName);
        }
        if (trainingMode.equals("Sandbox")) {
            startSandboxMode(inflater, container);
        } else {
            currentPads = db.templateDao().getTemplate("Chromatic").getPads();
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
        switch (v.getId()){
            case R.id.playlist:
                mPlayButton.setEnabled(true);
                mNextButton.setEnabled(true);
                mPrevButton.setEnabled(true);
                playlistSelect();
                break;
            case R.id.select:
                Intent intent = new Intent(getActivity(), SelectTemplateActivity.class);
                intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
                break;
            case R.id.prev:
                mSpotify.prevTrack();
            case R.id.play:
                GameStart = true;
                if (this.trainingMode.equals("Spotify")) {
                    mSpotify.play();
                } else {
                    playRandomNote(false);
                }
                break;
            case R.id.next:
                if (this.trainingMode.equals("Spotify")) {
                    mSpotify.nextTrack();
                } else {
                    mNextButton.setEnabled(false);
                    playRandomNote(true);
                }
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
            if(this.templateName != null) {
                Template result = templateTask.execute(this.templateName).get();
                currentPads = result.getPads();
            } else {
                Template result = templateTask.execute("C Major").get();
                currentPads = result.getPads();
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        setOnClickListeners();
    }

    private class GetCurrentTemplateTask extends AsyncTask<String, Void, Template> {
        Template currentTemplate;

        @Override
        protected Template doInBackground(String... name) {
            return db.templateDao().getTemplate(name[0]);
        }

        protected void onProgressUpdate() {
        }

        protected void onPostExecute() {
        }
    }

    private void startTrainingMode(LayoutInflater inflater, ViewGroup container) {
        getActivity().setTitle("TuneTrain - " + trainingMode + " Training");
        if (this.trainingMode.equals("Spotify")) {
            v = inflater.inflate(R.layout.fragment_spotify, container, false);
            setOnClickListeners();
            startSpotifyMode();
        } else {
            v = inflater.inflate(R.layout.fragment_note, container, false);
            setOnClickListeners();

        }
        toast = Toast.makeText(getActivity(), "CORRECT", Toast.LENGTH_SHORT);


    }
    private void startSpotifyMode() {

        mPrevButton = v.findViewById(R.id.prev);
        mPrevButton.setOnClickListener(this);

        mPlaylistButton = v.findViewById(R.id.playlist);
        mPlaylistButton.setOnClickListener(this);

        mPlayButton.setText(R.string.spotify_play);

        mPlayButton.setEnabled(false);
        mNextButton.setEnabled(false);
        mPrevButton.setEnabled(false);
        mPlaylistButton.setEnabled(true);
        mSpotify = SpotifySession.getSpotifyInstance(getActivity());
    }

    private void playlistSelect() {

        Spinner dropdown = v.findViewById(R.id.playlist_spinner);
        dropdown.setVisibility(View.VISIBLE);
        List<PlaylistSimple> playlists = mSpotify.getMyPlaylists();
        Log.d(TAG, Integer.toString(playlists.size()));
        String[] playlistNames = new String[playlists.size()+1];
        playlistNames[0] = "Select a Playlist";
        for (int x = 1; x < playlistNames.length; x++) {
            playlistNames[x] = playlists.get(x-1).name;
            Log.d(TAG, playlists.get(x-1).name);

        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, playlistNames);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "Position: " + position);
                if (position != 0) {
                    PlaylistSimple selectedPlaylist = playlists.get(position-1);
                    Log.d(TAG, selectedPlaylist.name);
                    Log.d(TAG, selectedPlaylist.uri);
                    mSpotify.setCurrentPlaylist(selectedPlaylist);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
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
        mPlayButton = v.findViewById(R.id.play);
        mPlayButton.setOnClickListener(this);

        mNextButton = v.findViewById(R.id.next);
        mNextButton.setOnClickListener(this);
        mNextButton.setEnabled(false);

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
    public void playRandomNote(boolean newNote) {
        if (newNote) {
            rIndex = r.nextInt(currentPads.length);
        }
        randomNote = currentPads[rIndex].noteName;
        mSoundPool.play(sounds[rIndex], 1, 1, 0, 0, 1);

    }

    public void handleNotePress() {
        if (trainingMode.equals("Note") && GameStart) {
            if (lastPlayed.equals(randomNote)) {
                db.guessDao().insert(new Guess("training", true, this.startOfSession));
                Log.d(TAG, "CORRECT");
                toast.setText("CORRECT");
                toast.show();
                mNextButton.setEnabled(true);
            } else {
                db.guessDao().insert(new Guess("training", false, this.startOfSession));
                toast.setText("INCORRECT, TRY AGAIN");
                toast.show();
                Log.d(TAG, "INCORRECT");
            }
        } else if (trainingMode.equals("Spotify")) {
            if (lastPlayed.equals(mSpotify.getCurrentKey())) {
                db.guessDao().insert(new Guess("training", true, this.startOfSession));
                Log.d(TAG, "CORRECT");
                toast.setText("CORRECT");
                toast.show();
            }   else {
                db.guessDao().insert(new Guess("training", false, this.startOfSession));
                toast.setText("INCORRECT, TRY AGAIN");
                toast.show();
                Log.d(TAG, "INCORRECT");
            }
        }

        if (this.startOfSession) {
            this.startOfSession = false;
        }

    }

}
