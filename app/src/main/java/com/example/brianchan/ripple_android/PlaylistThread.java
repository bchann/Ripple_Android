package com.example.brianchan.ripple_android;

/**
 * Created by Parikshit on 2/26/17.
 */

public class PlaylistThread extends Thread {
    private long songDuration;
    private Playlist playlist;

    public PlaylistThread(long sD, Playlist playlist) {
        songDuration = sD;
        this.playlist = playlist;
    }

    public void run() {
        try {
            sleep(songDuration);
            playlist.playNextSong();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}