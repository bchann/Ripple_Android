package com.example.brianchan.ripple_android;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Created by rishi on 3/3/17.
 */

public class Requests {
    private List<Song> requests;
    private Party party;

    public Requests(Party party) {
        this.requests = new ArrayList<>();
        this.party = party;
    }

    public Song pop() {
        return requests.remove(0);
    }

    public void push(Song song) {
        requests.add(song);
    }

    public boolean isEmpty() {
        return requests.size() == 0;
    }

    public Song peek() {
        return requests.get(0);
    }
}
