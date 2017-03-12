package com.example.brianchan.ripple_android;

import android.util.Log;

/**
 * Created by Parikshit on 2/26/17.
 */

public class PlaylistThread extends Thread {
    private long songDuration;
    private Playlist playlist;
    private boolean paused;
    private boolean finished;
    private Object pauseLock = new Object();

    public PlaylistThread(long sD, Playlist playlist) {
        songDuration = sD;
        this.playlist = playlist;
    }

    public void run() {
        while(!finished) {
            try {
                sleep(songDuration);
                playlist.playNextSong();
            } catch (InterruptedException e) {
                playlist.playNextSong();
            }

            synchronized (pauseLock) {
                while (paused) {
                    try {
                        Log.d("Debug", "We're waiting");
                        wait();
                    } catch (InterruptedException e) {
                    }
                }
            }
        }
    }


    public void onPause(){
        synchronized (pauseLock){
            paused = true;
        }
    }


    public void onResume(){
        synchronized (pauseLock){
            paused = false;
            pauseLock.notifyAll();
        }

    }


}