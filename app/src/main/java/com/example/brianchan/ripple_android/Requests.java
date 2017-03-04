package com.example.brianchan.ripple_android;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Created by rishi on 3/3/17.
 */

public class Requests {
    private Deque<Song> requests;
    private Party2 party2;

    public Requests(Party2 party2) {
        this.requests = new ArrayDeque<>();
        this.party2 = party2;
    }

    public Song pop() {
        return requests.pop();
    }

    public void push(Song song) {
        requests.push(song);
    }
}
