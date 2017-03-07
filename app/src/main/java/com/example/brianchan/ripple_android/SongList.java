package com.example.brianchan.ripple_android;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Brian Chan on 3/6/2017.
 */

public class SongList {
    public String list_type;
    public String party_id;
    public List<Song> songs;

    SongList(){}

    SongList(String list_type, String party_id, List<Song> songs) {
        this.list_type = list_type;
        this.party_id = party_id;
        this.songs = songs;
    }
}
