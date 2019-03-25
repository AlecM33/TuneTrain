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

        builder.setScopes(new String[]{"streaming"});
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

    public void disconnect() {
        SpotifyAppRemote.disconnect(mSpotifyAppRemote);
    }

    private void connected() {
        mSpotifyAppRemote.getPlayerApi().play("spotify:playlist:5g51BhC4yPIeyGzQBa2Oau");
        mSpotifyAppRemote.getPlayerApi()
                .subscribeToPlayerState()
                .setEventCallback(playerState -> {
                    final Track track = playerState.track;
                    if (track != null) {
                        Log.d(TAG, track.name + " by " + track.artist.name);
                    }
                });
    }

}
