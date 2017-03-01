package com.example.brianchan.ripple_android;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Brian Chan on 2/27/2017.
 */

public class PasscodeActivity extends AppCompatActivity {
    int passcode = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passcode);

        Party party = new Party();
        passcode = party.getPasscode();
    }

    public void enterParty(View view) {

    }

    public void sendSMS(View view) {
        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        sendIntent.setData(Uri.parse("sms:"));
        sendIntent.putExtra("sms_body", "My Ripple party passcode is " + passcode + " !");
        startActivity(sendIntent);
    }
}
