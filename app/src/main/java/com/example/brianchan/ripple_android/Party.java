package com.example.brianchan.ripple_android;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.google.firebase.database.FirebaseDatabase.getInstance;

/**
 * Created by rishi on 3/3/17.
 */

public class Party {
    private Requests requests;
    private Playlist playlist;
    private History history;
    private DJ dj;

    public static String id;
    public static String history_id, request_list_id, playlist_id;
    public static String user_list_id;
    public static int passcode = 1234;
    public static String spotifyAuth = "tempSpotifyAuthKey";
    public static String username = "tempUsername";

    private boolean valid = false;
    private boolean requests_paused = false;
    private static final int PASSCODE_LENGTH = 4;

    private final FirebaseDatabase database = getInstance();

    Party() {
        //Room references for the different database containers
        DatabaseReference partyRef = database.getReference("parties");
        id = partyRef.push().getKey();

        DatabaseReference roomRef = partyRef.child(id);
        DatabaseReference songsRef = database.getReference("songlists");
        DatabaseReference djRef = database.getReference("userlists");

        dj = new DJ();
        requests = new Requests(this);
        playlist = new Playlist(this);
        history = new History(this);

        /* Generate random unique passcode */
        while (!valid) {
            passcode = passcodeGen();

            partyRef.child(Integer.toString(passcode)).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if (snapshot.getValue() == null) {
                        valid = true;
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {}
            });
        }

        passcode = 1234; //Temp passcode

        history_id = songsRef.push().getKey();
        songsRef.child(history_id).child("songs").setValue(history);
        songsRef.child(history_id).child("party_id").setValue(id);
        songsRef.child(history_id).child("list_type").setValue("history");

        request_list_id = songsRef.push().getKey();
        songsRef.child(request_list_id).child("songs").setValue(requests);
        songsRef.child(request_list_id).child("party_id").setValue(id);
        songsRef.child(request_list_id).child("list_type").setValue("request");

        playlist_id = songsRef.push().getKey();
        songsRef.child(playlist_id).child("songs").setValue(playlist);
        songsRef.child(playlist_id).child("party_id").setValue(id);
        songsRef.child(playlist_id).child("list_type").setValue("playlist");

        //Set up user list with an initial user as the dj
        ArrayList<DJ> users = new ArrayList<>();
        users.add(dj);
        user_list_id = djRef.push().getKey();
        djRef.child(user_list_id).child("users").setValue(users);
        djRef.child(user_list_id).child("party_id").setValue(id);

        //Set up FIREBASE members
        roomRef.child("history_id").setValue(history_id);
        roomRef.child("request_list_id").setValue(request_list_id);
        roomRef.child("playlist_id").setValue(playlist_id);
        roomRef.child("user_list_id").setValue(user_list_id);
        roomRef.child("passcode").setValue(passcode);
        roomRef.child("requests_paused").setValue(requests_paused);

        //Song list listeners
        songsRef.child(Firebase.request_list_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                requests = dataSnapshot.getValue(Requests.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.err.println("The read failed: " + databaseError.getCode());
            }
        });

        songsRef.child(Firebase.playlist_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                playlist = dataSnapshot.getValue(Playlist.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.err.println("The read failed: " + databaseError.getCode());
            }
        });

        songsRef.child(Firebase.history_id).addValueEventListener(new ValueEventListener() {
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

    /**
     * Generates a random unique 4-digit integer passcode for the room.
     * @return Random unique 4-digit integer passcode.
     */
    private int passcodeGen() {
        String passcode = "";

        for (int i = 0; i < PASSCODE_LENGTH; i++) {
            int randint = (int) (Math.random() * 9);
            passcode += randint;
        }
        return Integer.parseInt(passcode);
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
