package com.example.brianchan.ripple_android;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by rishi on 3/3/17.
 */

public class DJ {
    private String username;

    public DJ() {
        // TODO: fetch username from Spotify, save in Firebase

        FirebaseDatabase database = FirebaseDatabase.getInstance();
    }

    public String getUsername() {
        return username;
    }
}
