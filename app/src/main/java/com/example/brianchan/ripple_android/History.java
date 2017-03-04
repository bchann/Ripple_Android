package com.example.brianchan.ripple_android;

import java.util.ArrayList;
import java.util.ListIterator;

/**
 * Created by rishi on 3/3/17.
 */

public class History {
    private ArrayList<Song> history;
    private Party2 party2;

    public History(Party2 party2) {
        this.party2 = party2;
        this.history = new ArrayList<Song>();

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
