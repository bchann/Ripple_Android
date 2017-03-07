package com.example.brianchan.ripple_android;

import android.content.Intent;
import android.net.Uri;
import android.view.View;

import static com.example.brianchan.ripple_android.Global.party;


/**
 * Created by Parikshit on 3/5/17.
 */

public class PasscodePresenter {

    private PasscodeActivity activity;

    public PasscodePresenter(PasscodeActivity activity){
        this.activity = activity;
    }

    public void enterParty(View view){
        Intent intent = new Intent(activity, PlaylistActivity.class);
        activity.startActivity(intent);
    }

    public void sendSMS(View view){
        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        sendIntent.setData(Uri.parse("sms:"));
        sendIntent.putExtra("sms_body", "My Ripple FIREBASE passcode is " + Global.party.getPasscode() + " !");
        activity.startActivity(sendIntent);
    }

}
