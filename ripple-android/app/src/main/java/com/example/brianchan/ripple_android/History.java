package com.example.brianchan.ripple_android;

import java.util.LinkedList;
import java.util.List;
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
        List<Song> newList = new LinkedList<>();
        newList.add(song);
        if (songs != null) {
            for (Song song1 : songs) {
                newList.add(song1);
            }
        }

        songs = newList;
    }

    public void export() {
        // TODO: export to DJ's premium account
    }

    public ListIterator<Song> listIterator() {
        return songs.listIterator();
    }
}
