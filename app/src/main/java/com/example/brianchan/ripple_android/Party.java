package com.example.brianchan.ripple_android;

import com.example.brianchan.ripple_android.DJ;
import com.example.brianchan.ripple_android.History;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by rishi on 3/3/17.
 */
public class Party {
    private Requests requests;
    private Playlist pl;
    private History history;

    private DJ dj;

    private int id;
    //private int passcode;

    //temp
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference partyRef = database.getReference("parties");
    private int roomid;
    private String history_id, request_list_id, playlist_id;
    public static String user_list_id;
    private int passcode = 1234;
    private boolean valid = false;
    private boolean requests_paused = false;
    private String spotifyAuth = "tempSpotifyAuthKey";
    private String username = "tempUsername";
    private static final int passcodeLength = 4;
    private List<SongDB> history_list, request_list, playlist;

    public Party() {

        // TODO: create FIREBASE on firebase, save party_id to this.id

        /* Generate random unique passcode */
        /*while (!valid) {
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
        }*/

        passcode = 1234; //Temp reassign
        roomid = passcode;

        //Room references for the different database containers
        DatabaseReference roomRef = partyRef.child("" + roomid);
        DatabaseReference songsRef = database.getReference("songlists");
        DatabaseReference djRef = database.getReference("userlists");

        //Set up the three song lists
        List<SongDB> songs = new LinkedList<>();
        songs.add(new SongDB("null", "null", "null"));
        history_id = songsRef.push().getKey();
        songsRef.child(history_id).child("songs").setValue(songs);
        songsRef.child(history_id).child("party_id").setValue(roomid);
        songsRef.child(history_id).child("list_type").setValue("history");

        request_list_id = songsRef.push().getKey();
        songsRef.child(request_list_id).child("songs").setValue(songs);
        songsRef.child(request_list_id).child("party_id").setValue(roomid);
        songsRef.child(request_list_id).child("list_type").setValue("request");

        playlist_id = songsRef.push().getKey();
        songsRef.child(playlist_id).child("songs").setValue(songs);
        songsRef.child(playlist_id).child("party_id").setValue(roomid);
        songsRef.child(playlist_id).child("list_type").setValue("playlist");

        //Set up user list with an initial user as the dj
        List<User> users = new LinkedList<>();
        users.add(new User("userid", "dj"));
        user_list_id = djRef.push().getKey();
        djRef.child(user_list_id).child("users").setValue(users);
        djRef.child(user_list_id).child("party_id").setValue(roomid);

        //Set up FIREBASE members
        roomRef.child("history_id").setValue(history_id);
        roomRef.child("request_list_id").setValue(request_list_id);
        roomRef.child("playlist_id").setValue(playlist_id);
        roomRef.child("user_list_id").setValue(user_list_id);
        roomRef.child("passcode").setValue(this.passcode);
        roomRef.child("requests_paused").setValue(requests_paused);

        changePasscode();

        dj = new DJ();

        requests = new Requests(this);
        pl = new Playlist(this);
        history = new History(this);
    }

    private void startParty() {
        // TODO: generate partyid, push to firebase
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
    }

    public int getId() {
        return id;
    }

    public String getDJName() {
        return dj.getUsername();
    }

    public History getHistory() {
        return history;
    }

    public Playlist getPlaylist() {
        return pl;
    }

    public Requests getRequests() {
        return requests;
    }
}
