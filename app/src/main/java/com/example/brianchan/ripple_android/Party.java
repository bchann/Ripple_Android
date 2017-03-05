package com.example.brianchan.ripple_android;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.google.firebase.database.FirebaseDatabase.getInstance;

/**
 * Created by rishi on 3/3/17.
 */
public class Party {
    private Requests requests;
    private Playlist playlist;
    private History history;
    private DJ dj;
    private String id;
    private int passcode;

    //Access this to get all member variables
    public static Firebase firebase;
    private final FirebaseDatabase database = getInstance();

    Party() {
        dj = new DJ();

        requests = new Requests(this);
        playlist = new Playlist(this);
        history = new History(this);

        firebase = new Firebase();
        passcode = firebase.getPasscode();

        DatabaseReference songlistRef = database.getReference("songlists");

        //Song list listeners
        songlistRef.child(Firebase.request_list_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                requests = dataSnapshot.getValue(Requests.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.err.println("The read failed: " + databaseError.getCode());
            }
        });

        songlistRef.child(Firebase.playlist_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                playlist = dataSnapshot.getValue(Playlist.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.err.println("The read failed: " + databaseError.getCode());
            }
        });

        songlistRef.child(Firebase.history_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                history = dataSnapshot.getValue(History.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.err.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    public int getPasscode() {
        return passcode;
    }

    public void changePasscode() {
        // TODO: generate unique passcode
        // TODO: push unique passcode to Firebase
    }

    public void roll() {
        // TODO: delete FIREBASE off firebase
        // Let's not roll rn
    }

    public String getId() {
        return id;
    }

    public String getDJName() {
        return dj.getUsername();
    }

    public History getHistory() {
        return history;
    }

    public Playlist getPlaylist() {
        return playlist;
    }

    public Requests getRequests() {
        return requests;
    }
}
