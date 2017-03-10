package com.example.brianchan.ripple_android;

/**
 * Created by Brian Chan on 3/9/2017.
 */

public class Notification {
    public String song_artist;
    public String song_img;
    public String song_name;
    public String song_time;
    public String status;

    Notification() {}

    public Notification(String song_artist, String song_img, String song_name, String song_time,
                        String status) {
        this.song_artist = song_artist;
        this.song_img = song_img;
        this.song_name = song_name;
        this.song_time = song_time;
        this.status = status;
    }
}
