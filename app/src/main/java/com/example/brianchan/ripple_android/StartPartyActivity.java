package com.example.brianchan.ripple_android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Brian Chan on 2/28/2017.
 */

public class StartPartyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_party);

    }

    public void startParty(View view) {
        Intent intent = new Intent(StartPartyActivity.this, PasscodeActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {}
}
