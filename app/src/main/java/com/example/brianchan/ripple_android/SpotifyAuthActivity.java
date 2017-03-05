package com.example.brianchan.ripple_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Brian Chan on 2/28/2017.
 */

public class SpotifyAuthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_spotify_auth);
    }

    public void startParty(View view) {
        Intent intent = new Intent(SpotifyAuthActivity.this, PasscodeActivity.class);
        startActivity(intent);
    }
}
