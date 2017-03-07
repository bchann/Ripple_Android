package com.example.brianchan.ripple_android;

import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;

import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Error;
import com.spotify.sdk.android.player.PlayerEvent;
import com.spotify.sdk.android.player.Spotify;
import com.spotify.sdk.android.player.SpotifyPlayer;

import static com.google.firebase.database.FirebaseDatabase.getInstance;

/**
 * Created by rishi on 2/26/17.
 */

public class AuthPresenter {

    public static final int REQUEST_CODE = 1337;
    public static final String CLIENT_ID = "7bccf322d5644ad4905b43e7d0f61f7f";
    public static final String REDIRECT_URI = "abg110://callback";

    AuthActivity activity;
    RipplePlayer rip;

    public AuthPresenter(AuthActivity activity) {
        this.activity = activity;

        rip = new RipplePlayer();
        Global.httpRequestQueue = Volley.newRequestQueue(activity.getApplicationContext());
    }

    public void connect() {
        AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder(CLIENT_ID,
                AuthenticationResponse.Type.TOKEN,
                REDIRECT_URI);
        builder.setScopes(new String[]{"user-read-private", "streaming"});
        AuthenticationRequest request = builder.build();

        AuthenticationClient.openLoginActivity(activity, REQUEST_CODE, request);
    }

    public void onDestroy() {
        //Avoids leaking resources
        Spotify.destroyPlayer(Global.player);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        // Check if result comes from the correct activity
        if (requestCode == REQUEST_CODE) {
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, intent);
            if (response.getType() == AuthenticationResponse.Type.TOKEN) {
                Config playerConfig = new Config(activity, response.getAccessToken(), CLIENT_ID);
                Spotify.getPlayer(playerConfig, this, new SpotifyPlayer.InitializationObserver() {
                    @Override
                    public void onInitialized(SpotifyPlayer spotifyPlayer) {
                        Global.player = spotifyPlayer;
                        Global.player.addConnectionStateCallback(rip);
                        Global.player.addNotificationCallback(rip);

                        //Redundant party check
                        final FirebaseDatabase database = getInstance();
                        DatabaseReference partyRef = database.getReference("parties");
                        String party_id = "1234"; //TODO: IMPLEMENT LINKING SPOTIFY TO PARTYID

                        partyRef.child(party_id).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    Global.party = new Party(
                                            snapshot.child("party_id").getValue(String.class),
                                            snapshot.child("history_id").getValue(String.class),
                                            snapshot.child("request_list_id").getValue(String.class),
                                            snapshot.child("playlist_id").getValue(String.class),
                                            snapshot.child("user_list_id").getValue(String.class),
                                            snapshot.child("passcode").getValue(Integer.class));
                                }
                                else {
                                    Global.party = new Party();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {}
                        });

                        Intent intent = new Intent(activity, StartPartyActivity.class);
                        activity.startActivity(intent);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Log.e("AuthActivity", "Could not initialize player: " + throwable.getMessage());
                    }
                });
            }
        }
    }

    class RipplePlayer implements SpotifyPlayer.NotificationCallback, ConnectionStateCallback {

        // Implementing NotificationCallback methods
        @Override
        public void onPlaybackEvent(PlayerEvent playerEvent) {
            Log.d("AuthActivity", "Playback event received: " + playerEvent.name());
            switch (playerEvent) {
                // Handle event type as necessary
                default:
                    break;
            }
        }

        @Override
        public void onPlaybackError(Error error) {
            Log.d("AuthActivity", "Playback error received: " + error.name());
            switch (error) {
                // Handle error type as necessary
                default:
                    break;
            }
        }
        // End implementing NotificationCallback methods

        // Implementing ConnectionStateCallback methods
        @Override
        public void onLoggedIn() {
            Log.d("AuthActivity", "User logged in");
        }

        @Override
        public void onLoggedOut() {
            Log.d("AuthActivity", "User logged out");
        }

        @Override
        public void onLoginFailed(Error error) {
            Log.d("AuthActivity", "Login failed");
        }

        @Override
        public void onTemporaryError() {
            Log.d("AuthActivity", "Temporary error occurred");
        }

        @Override
        public void onConnectionMessage(String message) {
            Log.d("AuthActivity", "Received connection message: " + message);
        }
        // End implementing ConnectionStateCallback methods
    }
}