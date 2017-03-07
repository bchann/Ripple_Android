package com.example.brianchan.ripple_android;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Brian Chan on 2/28/2017.
 */

public class StartPartyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_party);

        TextView login = (TextView) findViewById(R.id.logintext);
        login.setText("Logged in as: " );
    }

    public void startParty(View view) {
        Intent intent = new Intent(StartPartyActivity.this, PasscodeActivity.class);
        startActivity(intent);
    }
}
