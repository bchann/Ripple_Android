package com.example.brianchan.ripple_android;

import android.util.Log;

import com.spotify.sdk.android.player.Error;
import com.spotify.sdk.android.player.Player;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import static com.example.brianchan.ripple_android.Global.player;


/**
 * Created by Parikshit on 2/26/17.
 */

public class Playlist extends SongList {
    private Party party;
    private PlaylistThread nextSongThread;
    private boolean firstTime;

    Player.OperationCallback op;

    Playlist() {}

    Playlist(Party party){
        super("playlist", party.getId(), new LinkedList<Song>());
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
    }

    public void enqueue(Song song) {
        songs.add(song);

        String songName = song.getTitle();
    }

    public Song dequeue() {
        return songs.remove(0);
    }

    private void remove(Song song) {
        songs.remove(song);
    }

    public void togglePlayPause(){
        if(player.getPlaybackState().isPlaying){
            player.pause(op);
            songs.get(0).markPaused();
            try {
                nextSongThread.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else{
            if(player.getPlaybackState().positionMs < player.getMetadata().currentTrack.durationMs){
                player.resume(op);
                songs.get(0).markPlaying();
                notify();
            }
        }
    }

    //plays the next song on our playlist
    public void playNextSong(){
        Song currSong = songs.get(0);
        currSong.markFinishedPlaying();

        if(firstTime) {
            nextSongThread = new PlaylistThread(2000, this);
            player.playUri(op, currSong.getUri(), 0, 180000);
            nextSongThread.start();

            firstTime = !firstTime;
        }
        else{
            remove(currSong);
            if(songs.size() != 0) {
                Song nextSong =  songs.get(0);
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
        Song skippedSong = songs.get(0);
        remove(skippedSong);
        skippedSong.markFinishedPlaying();
    }

    public List<Song> reorder(int fromIndex, int toIndex){
        Song toMove = songs.remove(fromIndex);
        songs.add(toIndex, toMove);
        return songs;
    }

    public ListIterator<Song> listIterator(){
        return songs.listIterator();
    }

    public Song getCurrSong(){ return songs.get(0);}
}
