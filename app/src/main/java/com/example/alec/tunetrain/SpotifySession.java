package com.example.alec.tunetrain;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Looper;
import android.util.Log;

import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.protocol.types.Track;
import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyCallback;
import kaaes.spotify.webapi.android.SpotifyError;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.AudioFeaturesTrack;
import kaaes.spotify.webapi.android.models.Pager;
import kaaes.spotify.webapi.android.models.PlaylistSimple;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.Response;


public class SpotifySession {

    private static SpotifySession INSTANCE;
    private Activity mActivity;

    //SDK Authorization Vars - TuneTrain Dev App Specific
    private static final String TAG = "SpotifyWrapper";
    private static final int REQUEST_CODE = 1337;
    private static final String CLIENT_ID = "8a60234733ab4353946cff8fb3c9c90c";
    private static final String REDIRECT_URI = "tune-train-login://callback";
    private String authToken = "";

    //Services SDK and WebApi
    private SpotifyAppRemote mSpotifyAppRemote;
    private SpotifyService WebApi;

    //Current Playing Info
    private String currentTrackID;
    private String currKey;
    private boolean isPlaying = false;

    //User Playlist Info
    private List<PlaylistSimple> mMyPlaylists;
    private PlaylistSimple currPlaylist;

    //Static Map of Key ints from Spotify API to Note Names
    private static HashMap<Integer, String> keys = createKeys();


    private SpotifySession(Activity activity) {
        this.mActivity = activity;
        buildSession();
    }

    public static SpotifySession getSpotifyInstance(Activity activity) {
        if (INSTANCE == null) {
            INSTANCE = new SpotifySession(activity);
        }

        return INSTANCE;
    }



    /*
     * Start building the spotify SDK session with proper scopes needed for the app
     */
    private void buildSession() {
        AuthenticationRequest.Builder builder =
                new AuthenticationRequest.Builder(CLIENT_ID, AuthenticationResponse.Type.TOKEN, REDIRECT_URI);

        builder.setScopes(new String[]{"streaming", "user-read-playback-state", "playlist-read-private", "user-read-private"});
        AuthenticationRequest request = builder.build();
        AuthenticationClient.openLoginActivity(this.mActivity, REQUEST_CODE, request);


    }

    /*
     * HashMap representing the number to String key system that
     * Spotify's WebAPI documented
     * i.e. if a song on the WebAPI is in the key of "0" it means
     * the song is in C
     */
    private static HashMap<Integer, String> createKeys() {
        HashMap<Integer, String> result = new HashMap<>();
        result.put(-1, "none");
        result.put(0, "C");
        result.put(1, "Db");
        result.put(2, "D");
        result.put(3, "Eb");
        result.put(4, "E");
        result.put(5, "F");
        result.put(6, "Gb");
        result.put(7, "G");
        result.put(8, "Ab");
        result.put(9, "A");
        result.put(10, "Bb");
        result.put(11, "B");

        return result;
    }

    /*
     * Connect to Spotify Android SDK with the CLIENT_ID
     */
    public void connect() {

            ConnectionParams connectionParams =
                    new ConnectionParams.Builder(CLIENT_ID)
                            .setRedirectUri(REDIRECT_URI)
                            .showAuthView(true)
                            .build();

            SpotifyAppRemote.connect(mActivity, connectionParams,
                    new Connector.ConnectionListener() {

                        @Override
                        public void onConnected(SpotifyAppRemote spotifyAppRemote) {
                            mSpotifyAppRemote = spotifyAppRemote;
                            Log.d(TAG, "Connected! Yay!");
                            // Now you can start interacting with App Remote
                            connected();
                        }

                        @Override
                        public void onFailure(Throwable throwable) {
                            Log.e(TAG, throwable.getMessage(), throwable);
                            Log.d(TAG, "Error Connecting to Spotify");
                            // Something went wrong when attempting to connect! Handle errors here
                        }
                    });
    }

    /*
     * Continuously get the new info while connected to Spotify
     */
    private void connected() {
        mSpotifyAppRemote.getPlayerApi()
                .subscribeToPlayerState()
                .setEventCallback(playerState -> {
                    final Track track = playerState.track;
                    currentTrackID = track.uri;
                    if (track != null) {
                        Log.d(TAG, track.name + " by " + track.artist.name);
                        Log.d(TAG, currentTrackID);
                    }
                    SpotifySession.GetTrackInfo trackInfoTask = new SpotifySession.GetTrackInfo();
                    try {
                        trackInfoTask.execute().get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
    }

    /*
     * Disconnect from Spotify API
     */
    public void disconnect() {
        SpotifyAppRemote.disconnect(mSpotifyAppRemote);
    }


    /*
     * Get the authentication response token from Spotfiy for access to the Android SDK
     * Runs on a seperate thread for efficiency
     */
    public void setResponse(int requestCode, int resultCode, Intent intent) {

        Log.d(TAG, "On Activity Result called, Spotify gets auth result");

        new Thread(() -> {
            Looper.prepare();
        // Check if result comes from the correct activity
        if (requestCode == REQUEST_CODE) {
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, intent);

            switch (response.getType()) {
                // Response was successful and contains auth token
                case TOKEN:
                    // Handle successful response
                    authToken = response.getAccessToken();
                    Log.d(TAG, authToken);
                    break;

                // Auth flow returned an error
                case ERROR:
                    // Handle error response
                    Log.e(TAG, "ERROR getting Spotify Auth");
                    break;

                // Most likely auth flow was cancelled
                default:
                    // Handle other cases
            }
        }

        setUpWebApi();
        }).start();

    }

    /*
        Set up REST connection to Spotify's web API.
     */
    private void setUpWebApi() {

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(SpotifyApi.SPOTIFY_WEB_API_ENDPOINT)
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addHeader("Authorization", "Bearer " + authToken);
                    }
                })
                .build();

        WebApi = restAdapter.create(SpotifyService.class);
        SpotifySession.GetPlaylists playlistsTask = new SpotifySession.GetPlaylists();
        try {
            mMyPlaylists = playlistsTask.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    //Async Task for getting the Playlists from the WebAPI
    private class GetPlaylists extends AsyncTask<Void, Void, List<PlaylistSimple>> {

        @Override
        protected List<PlaylistSimple> doInBackground(Void... params){
            List<PlaylistSimple> mMyPlaylists = new ArrayList<>();
            WebApi.getMyPlaylists(new SpotifyCallback<Pager<PlaylistSimple>>() {
                @Override
                public void success(Pager<PlaylistSimple> playlists, Response response) {
                    // handle successful response
                    for (PlaylistSimple playlist : playlists.items) {
                        Log.d(TAG, playlist.name);
                        mMyPlaylists.add(playlist);
                    }

                }

                @Override
                public void failure(SpotifyError error) {
                    // handle error
                    Log.e(TAG, error.getMessage());
                    Log.d(TAG, "ERROR GETTING PLAYLISTS");
                }
            });

            return mMyPlaylists;
        }

        protected void onProgressUpdate() {
        }

        protected void onPostExecute() {
        }
    }


    //Async Task for getting the Track Info from the WebAPI
    private class GetTrackInfo extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params){
            Log.d(TAG, currentTrackID.substring(14));
            WebApi.getTrackAudioFeatures(currentTrackID.substring(14), new SpotifyCallback<AudioFeaturesTrack>() {
                @Override
                public void success(AudioFeaturesTrack info, Response response) {
                    // handle successful response
                    currKey = keys.get(info.key);
                    if (currKey.equals("none")) {
                        nextTrack();
                    }
                    Log.d(TAG, "Current Key of Song: " + currKey);
                }

                @Override
                public void failure(SpotifyError error) {
                    // handle error
                    Log.d(TAG, "Error Getting Track Info");
                }
            });
            return null;
        }

        protected void onProgressUpdate() {
        }

        protected void onPostExecute() {
        }
    }



    /*
     * Returns the current key of the song playing
     */
    public String getCurrentKey() {
        return this.currKey;
    }

    /*
     * Controls play/pause for Spotify player
     */
    public void play() {
        if (isPlaying) {
            mSpotifyAppRemote.getPlayerApi().pause();
            isPlaying = false;
        } else {
            mSpotifyAppRemote.getPlayerApi().resume();
            isPlaying = true;
        }
    }

    /*
     * Plays the current playlist
     */
    private void playPlaylist() {
        mSpotifyAppRemote.getPlayerApi().play(this.currPlaylist.uri);
        isPlaying = true;
    }

    /*
     * Go to the next strack in Spotify queue
     */
    public void nextTrack() {
        mSpotifyAppRemote.getPlayerApi().skipNext();
    }

    /*
     * Go to previous track in Spotify queue
     */
    public void prevTrack() {
        mSpotifyAppRemote.getPlayerApi().skipPrevious();
    }

    /*
     * Return the currently Authorized Spotify Users playlists
     */
    public List<PlaylistSimple> getMyPlaylists() {
        return mMyPlaylists;
    }

    /*
     * Set the selected playlist the user wants to play
     */
    public void setCurrentPlaylist(PlaylistSimple playlist) {
        this.currPlaylist = playlist;
        Log.d(TAG, "CURRENT PLAYLIST: " + currPlaylist.name + " " + currPlaylist.uri);
        playPlaylist();
    }

}
