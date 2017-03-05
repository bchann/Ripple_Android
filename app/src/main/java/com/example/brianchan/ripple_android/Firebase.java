package com.example.brianchan.ripple_android;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Brian Chan on 2/5/2017.
 */

public class Firebase {
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    public static String roomid;
    public static String history_id, request_list_id, playlist_id;
    public static String user_list_id;
    public static int passcode = 1234;
    public static String spotifyAuth = "tempSpotifyAuthKey";
    public static String username = "tempUsername";
    public static List<SongDB> history_list, request_list, playlist;

    private boolean valid = false;
    private boolean requests_paused = false;
    private static final int PASSCODE_LENGTH = 4;

    /**
     * Creates a new FIREBASE in Firebase and links it with songlists, userlists, and users.
     */
    Firebase() {
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

        passcode = 1234; //Temp passcode

        //Room references for the different database containers
        DatabaseReference partyRef = database.getReference("parties");
        roomid = partyRef.push().getKey();

        DatabaseReference roomRef = partyRef.child(roomid);
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
        roomRef.child("passcode").setValue(passcode);
        roomRef.child("requests_paused").setValue(requests_paused);
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

    public int getPasscode(){
        return passcode;
    }
}

/**
 * A SongDB in a song list.
 */
class SongDB {
    public String song_name; //Name of the song
    public String song_id; //Spotify id of the song
    public String requester; //Person who requested the song
    public String status = "valid";

    /**
     * Constructor which just assigns the song attributes.
     * @param song_name Name of the song
     * @param song_id Spotify id of the song
     * @param requester Person who requested the song
     */
    public SongDB(String song_name, String song_id, String requester) {
        this.song_name = song_name;
        this.song_id = song_id;
        this.requester = requester;
    }
}

/**
 * A User in a user list.
 */
class User {
    public String userid;
    public String type;

    public User(String userid, String type) {
        this.userid = userid;
        this.type = type;
    }
}
