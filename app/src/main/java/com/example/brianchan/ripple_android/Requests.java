package com.example.brianchan.ripple_android;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

/**
 * Created by rishi on 3/3/17.
 */

public class Requests {
    public String list_type = "request";
    public String party_id;
    public ArrayList<Song> requests;

    private Party party;

    public Requests(Party party) {
        this.requests = new ArrayList<>();
        this.party = party;
        this.party_id = party.getId();
    }

    public Song pop() {
        return requests.remove(0);
    }

    public void push(Song song) {
        requests.add(song);
    }
}
