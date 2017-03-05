package com.example.brianchan.ripple_android;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Created by rishi on 3/3/17.
 */

public class Requests {
    private Deque<Song> requests;
    private Party party;

    public Requests(Party party) {
        this.requests = new ArrayDeque<>();
        this.party = party;
    }

    public Song pop() {
        return requests.pop();
    }

    public void push(Song song) {
        requests.push(song);
    }
}
