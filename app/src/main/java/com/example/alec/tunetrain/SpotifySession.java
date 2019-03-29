package com.example.alec.tunetrain;

import android.app.Activity;
import android.content.Intent;
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

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyCallback;
import kaaes.spotify.webapi.android.SpotifyError;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.AudioFeaturesTrack;
import kaaes.spotify.webapi.android.models.Pager;
import kaaes.spotify.webapi.android.models.PlaylistSimple;
import kaaes.spotify.webapi.android.models.UserPrivate;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.Response;


public class SpotifySession {

    private static final String TAG = "TrainingActivity";
    private static final int REQUEST_CODE = 1337;
    private static final String CLIENT_ID = "8a60234733ab4353946cff8fb3c9c90c";
    private static final String REDIRECT_URI = "tune-train-login://callback";

    private static AuthenticationResponse RESPONSE;
    private String authToken = "";
    private Activity mActivity;
    private static SpotifySession INSTANCE;
    private SpotifyAppRemote mSpotifyAppRemote;
    private String currentTrackID;
    private List<PlaylistSimple> mMyPlaylists;
    private PlaylistSimple currPlaylist;
    private boolean isPlaying = false;
    private String currKey;
    private static HashMap<Integer, String> keys = createKeys();

    private SpotifyService WebApi;

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


    private void buildSession() {
        AuthenticationRequest.Builder builder =
                new AuthenticationRequest.Builder(CLIENT_ID, AuthenticationResponse.Type.TOKEN, REDIRECT_URI);

        builder.setScopes(new String[]{"streaming", "user-read-playback-state", "playlist-read-private", "user-read-email",
        "user-read-private"});
        AuthenticationRequest request = builder.build();

        AuthenticationClient.openLoginActivity(this.mActivity, REQUEST_CODE, request);


    }

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

                        // Something went wrong when attempting to connect! Handle errors here
                    }
                });

    }

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
                    getTrackInfo();
                });
    }

    public void disconnect() {
        SpotifyAppRemote.disconnect(mSpotifyAppRemote);
    }


    public void setResponse(int requestCode, int resultCode, Intent intent) {

        Log.d(TAG, "On Activity Result called, Spotify gets auth result");

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
    }

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

        WebApi.getMe(new SpotifyCallback<UserPrivate>() {
            @Override
            public void success(UserPrivate savedUser, Response response) {
                // handle successful response
                Log.d(TAG, savedUser.display_name);
                Log.d(TAG, savedUser.email);
                Log.d(TAG, savedUser.id);
            }

            @Override
            public void failure(SpotifyError error) {
                // handle error
                Log.e(TAG, error.getMessage());
                Log.d(TAG, "ERROR GETTING USER");
            }
        });

        WebApi.getMyPlaylists(new SpotifyCallback<Pager<PlaylistSimple>>() {
            @Override
            public void success(Pager<PlaylistSimple> playlists, Response response) {
                // handle successful response
                mMyPlaylists = new ArrayList<>();
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

    }

    public void getTrackInfo() {

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
    }

    public String getCurrentKey() {
        return this.currKey;
    }


    public void play() {
        if (isPlaying) {
            mSpotifyAppRemote.getPlayerApi().pause();
            isPlaying = false;
        } else {
            mSpotifyAppRemote.getPlayerApi().resume();
            isPlaying = true;
        }
    }

    private void playPlaylist() {
        mSpotifyAppRemote.getPlayerApi().play(this.currPlaylist.uri);
        isPlaying = true;
    }

    public void nextTrack() {
        mSpotifyAppRemote.getPlayerApi().skipNext();
    }

    public void prevTrack() {
        mSpotifyAppRemote.getPlayerApi().skipPrevious();
    }

    public List<PlaylistSimple> getMyPlaylists() {
        return mMyPlaylists;
    }

    public void setCurrentPlaylist(PlaylistSimple playlist) {
        this.currPlaylist = playlist;
        playPlaylist();
    }

    public String getToken() {
        return this.authToken;
    }

}
