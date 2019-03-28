package com.example.alec.tunetrain;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.example.alec.tunetrain.Entities.Playlist;
import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.protocol.types.Track;
import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyCallback;
import kaaes.spotify.webapi.android.SpotifyError;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Album;
import kaaes.spotify.webapi.android.models.CurrentlyPlaying;
import kaaes.spotify.webapi.android.models.Pager;
import kaaes.spotify.webapi.android.models.PlaylistSimple;
import kaaes.spotify.webapi.android.models.UserPrivate;
import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
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
    }

    public void setUpWebApi() {

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(SpotifyApi.SPOTIFY_WEB_API_ENDPOINT)
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addHeader("Authorization", "Bearer " + authToken);
                    }
                })
                .build();


        SpotifyService spotify = restAdapter.create(SpotifyService.class);

        spotify.getCurrentlyPlaying("ES", new SpotifyCallback<CurrentlyPlaying>() {
            @Override
            public void success(CurrentlyPlaying track, Response response) {
                // handle successful response
                Log.d(TAG, track.item.name);
            }

            @Override
            public void failure(SpotifyError error) {
                // handle error
                Log.e(TAG, error.getMessage());
                Log.d(TAG, "ERROR GETTING CURRENT TRACK");
            }
        });


        spotify.getMe(new SpotifyCallback<UserPrivate>() {
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

        spotify.getMyPlaylists(new SpotifyCallback<Pager<PlaylistSimple>>() {
            @Override
            public void success(Pager<PlaylistSimple> playlists, Response response) {
                // handle successful response
                for (PlaylistSimple playlist : playlists.items) {
                    Log.d(TAG, playlist.name);
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

    public String getToken() {
        return this.authToken;
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
                        getUserID();
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.e(TAG, throwable.getMessage(), throwable);

                        // Something went wrong when attempting to connect! Handle errors here
                    }
                });

    }

    public void disconnect() {
        SpotifyAppRemote.disconnect(mSpotifyAppRemote);
    }

    private void connected() {
        mSpotifyAppRemote.getPlayerApi()
                .subscribeToPlayerState()
                .setEventCallback(playerState -> {
                    final Track track = playerState.track;
                    if (track != null) {
                        Log.d(TAG, track.name + " by " + track.artist.name);
                    }
                });
    }

    private void getUserID() {




    }

    public void play() {
        mSpotifyAppRemote.getPlayerApi().play("spotify:playlist:5g51BhC4yPIeyGzQBa2Oau");
    }

    public void nextTrack() {
        mSpotifyAppRemote.getPlayerApi().skipNext();
    }

    public void prevTrack() {
        mSpotifyAppRemote.getPlayerApi().skipPrevious();
    }

}
