package com.example.brianchan.ripple_android;

import com.example.brianchan.ripple_android.DJ;
import com.example.brianchan.ripple_android.History; /**
 * Created by rishi on 3/3/17.
 */
public class Party2 {
    private Requests requests;
    private Playlist playlist;
    private History history;

    private DJ dj;

    private int id;
    private int passcode;

    public Party2() {

        // TODO: create party on firebase, save party_id to this.id

        dj = new DJ();

        requests = new Requests(this);
        playlist = new Playlist(this);
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
        // TODO: delete party off firebase
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
        return playlist;
    }

    public Requests getRequests() {
        return requests;
    }
}
