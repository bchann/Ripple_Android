package com.example.brianchan.ripple_android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Brian Chan on 2/27/2017.
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Override monospace to be montessarrat LOL
        FontsOverride.setDefaultFont(this, "MONOSPACE", "fonts/montserrat.ttf");
    }

    public void spotifyAuth(View view) {
        Intent intent = new Intent(MainActivity.this, SpotifyAuthActivity.class);
        startActivity(intent);
    }
}
