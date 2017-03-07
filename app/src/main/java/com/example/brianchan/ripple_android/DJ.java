package com.example.brianchan.ripple_android;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by rishi on 3/3/17.
 */

public class DJ {
    public String username;

    DJ() {
        // TODO: fetch username from Spotify, save in Firebase

        username = "tempUsername";
    }

    public String getUsername() {
        return username;
    }
}
