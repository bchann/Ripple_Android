package com.example.brianchan.ripple_android;

import android.util.Log;

import com.spotify.sdk.android.player.Player;

import java.util.ArrayList;
import java.util.ListIterator;

import com.spotify.sdk.android.player.Error;

import static com.example.brianchan.ripple_android.Global.player;


/**
 * Created by Parikshit on 2/26/17.
 */

public class Playlist {
    private ArrayList<Song> songList;

    private Party party;

    private PlaylistThread nextSongThread;
    private boolean firstTime;

    Player.OperationCallback op;

    public Playlist(Party party){

        this.party = party;
        firstTime = true;

        op = new Player.OperationCallback() {
            @Override
            public void onSuccess() {
                Log.d("Success", "Callback successful");
            }

            @Override
            public void onError(Error error) {
                Log.d("Error", "Callback unsuccessful");
            }
        };

        songList = new ArrayList<Song>();
    }

    public void enqueue(Song song) {

        Song newSong = song;
        songList.add(newSong);

        String songName = newSong.getTitle();

        // TODO: push song to playlist
    }

    private void remove(Song song) {
        songList.remove(song);
        //TODO  remove song from playlist
    }

    public void togglePlayPause(){
        if(player.getPlaybackState().isPlaying){
            player.pause(op);
            songList.get(0).markPaused();
            try {
                nextSongThread.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else{
            if(player.getPlaybackState().positionMs < player.getMetadata().currentTrack.durationMs){
                player.resume(op);
                songList.get(0).markPlaying();
                notify();
            }

        }

    }

    //plays the next song on our playlist
    public void playNextSong(){
        Song currSong = songList.get(0);
        currSong.markFinishedPlaying();

        if(firstTime) {
            nextSongThread = new PlaylistThread(2000, this);
            player.playUri(op, currSong.getUri(), 0, 180000);
            nextSongThread.start();

            firstTime = !firstTime;
        }
        else{
            remove(currSong);
            if(songList.size() != 0) {
                Song nextSong =  songList.get(0);
                nextSongThread = new PlaylistThread(nextSong.getDuration(), this);
                player.playUri(op, nextSong.getUri(), 0, 0);
                nextSongThread.start();
                //hist.push(currSong);
            }
        }
    }

    //skips to next song
    public void skip(){
        nextSongThread.interrupt();
        Song skippedSong = songList.get(0);
        remove(skippedSong);
        skippedSong.markFinishedPlaying();
    }

    public ArrayList<Song> reorder(int fromIndex, int toIndex){
        Song toMove = songList.remove(fromIndex);
        songList.add(toIndex, toMove);
        return songList;
    }

    public ListIterator<Song> listIterator(){
        return songList.listIterator();
    }

    public Song getCurrSong(){ return songList.get(0);}
}
