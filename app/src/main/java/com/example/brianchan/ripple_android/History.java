package com.example.brianchan.ripple_android;

import java.util.ArrayList;
import java.util.ListIterator;

/**
 * Created by rishi on 3/3/17.
 */

public class History {
    public String list_type = "history";
    public String party_id;
    public ArrayList<Song> history;

    private Party party;

    public History(Party party) {
        this.party = party;
        this.history = new ArrayList<Song>();
        this.party_id = party.getId();

        // TODO: pull history from Firebase and load into song listby calling append
    }

    public void append(Song song) {
        history.add(song);

        // write appended song to history on Firebase
    }

    public void export() {
        // export to DJ's premium account
    }

    public ListIterator<Song> listIterator() {
        return history.listIterator();
    }
}
