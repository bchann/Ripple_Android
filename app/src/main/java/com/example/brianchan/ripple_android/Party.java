package com.example.brianchan.ripple_android;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.LinkedList;
import java.util.List;

import static com.google.firebase.database.FirebaseDatabase.getInstance;

/**
 * Created by rishi on 3/3/17.
 */

public class Party {
    private static final int PASSCODE_LENGTH = 4;
    public static String party_id;
    public static String history_id, request_list_id, playlist_id;
    public static String user_list_id;
    public static int passcode = 1234;
    public static String spotifyAuth = "tempSpotifyAuthKey";

    private Requests requests;
    private Playlist playlist;
    private History history;
    private DJ dj;
    private boolean valid = false;
    private boolean requests_paused = false;

    private final FirebaseDatabase database = getInstance();

    Party() {
        //Room references for the different database containers
        DatabaseReference partyRef = database.getReference("parties");
        //party_id = partyRef.push().getKey();
        party_id = "1234";

        DatabaseReference roomRef = partyRef.child(party_id);
        DatabaseReference songsRef = database.getReference("songlists");
        DatabaseReference djRef = database.getReference("userlists");

        dj = new DJ();
        history = new History(this);
        requests = new Requests(this);
        playlist = new Playlist(this);
        Log.d("Debug", "Created Playlist");

        passcode = 1234; //Temp passcode

        history_id = songsRef.push().getKey();
        //songsRef.child(history_id).child("songs").setValue(history);
        songsRef.child(history_id).child("party_id").setValue(party_id);
        songsRef.child(history_id).child("list_type").setValue("history");

        request_list_id = songsRef.push().getKey();
        //songsRef.child(request_list_id).child("songs").setValue(requests);
        songsRef.child(request_list_id).child("party_id").setValue(party_id);
        songsRef.child(request_list_id).child("list_type").setValue("request");

        playlist_id = songsRef.push().getKey();
        //songsRef.child(playlist_id).child("songs").setValue(playlist);
        songsRef.child(playlist_id).child("party_id").setValue(party_id);
        songsRef.child(playlist_id).child("list_type").setValue("playlist");

        //Set up user list with an initial user as the dj
        List<DJ> users = new LinkedList<>();
        users.add(dj);
        user_list_id = djRef.push().getKey();
        djRef.child(user_list_id).child("users").setValue(users);
        djRef.child(user_list_id).child("party_id").setValue(party_id);

        //Set up FIREBASE members
        roomRef.child("history_id").setValue(history_id);
        roomRef.child("request_list_id").setValue(request_list_id);
        roomRef.child("playlist_id").setValue(playlist_id);
        roomRef.child("user_list_id").setValue(user_list_id);
        roomRef.child("passcode").setValue(passcode);
        roomRef.child("requests_paused").setValue(requests_paused);
    }

    public Party(String party_id, String history_id, String request_list_id, String playlist_id,
                 String user_list_id, int passcode) {
        this.party_id = party_id;
        this.history_id = history_id;
        this.request_list_id = request_list_id;
        this.playlist_id = playlist_id;
        this.user_list_id = user_list_id;
        this.passcode = passcode;
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
        return party_id;
    }

    public String getDJName() {
        return dj.getUsername();
    }

    public void setRequests(Requests requests) {
        this.requests = requests;
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }

    public void setHistory(History history) {
        this.history = history;
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
