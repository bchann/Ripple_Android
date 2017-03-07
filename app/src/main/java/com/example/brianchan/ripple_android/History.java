package com.example.brianchan.ripple_android;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Created by rishi on 3/3/17.
 */

public class History extends SongList{
    private Party party;

    History() {}

    History(Party party) {
        super("history", party.getId(), new LinkedList<Song>());
        this.party = party;
    }

    public void append(Song song) {
        songs.add(song);
    }

    public void export() {
        // TODO: export to DJ's premium account
    }

    public ListIterator<Song> listIterator() {
        return songs.listIterator();
    }
}
