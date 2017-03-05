package com.example.brianchan.ripple_android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Brian Chan on 2/27/2017.
 */

public class AuthActivity extends AppCompatActivity {
    private AuthPresenter presenter;
    private Song song;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        //Override monospace to be montessarrat LOL
        FontsOverride.setDefaultFont(this, "MONOSPACE", "fonts/montserrat.ttf");

        presenter = new AuthPresenter(this);
    }

    // on connectBtn click
    public void connect(View v) {
        presenter.connect();
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        presenter.onActivityResult(requestCode, resultCode, intent);
    }
}
