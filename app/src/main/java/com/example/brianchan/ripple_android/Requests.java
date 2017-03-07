package com.example.brianchan.ripple_android;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by rishi on 3/3/17.
 */

public class Requests extends SongList{
    private Party party;

    Requests() {}

    Requests(Party party) {
        super("requests", party.getId(), new LinkedList<Song>());
        this.party = party;
    }

    public Song pop() {
        return songs.remove(0);
    }

    public void push(Song song) {
        songs.add(song);
    }

    public boolean isEmpty() {
        return songs.size() == 0;
    }

    public Song peek() {
        return songs.get(0);
    }
}
