package com.example.brianchan.ripple_android;

import android.util.Log;

import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Error;
import com.spotify.sdk.android.player.Player;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import static com.example.brianchan.ripple_android.Global.firstTime;
import static com.example.brianchan.ripple_android.Global.nextSongThread;
import static com.example.brianchan.ripple_android.Global.player;


/**
 * Created by Parikshit on 2/26/17.
 */

public class Playlist extends SongList {
    //private static Party party;

    private static Player.OperationCallback op;

    Playlist() {}

    Playlist(Party party){
        super("playlist", party.getId(), new LinkedList<Song>());
        //this.party = party;

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

    public void togglePlayPause() {
        if (!songs.isEmpty()) {
            if (player.getPlaybackState().isPlaying) {
                Log.d("debug", "pausing");
                player.pause(op);
                songs.get(0).markPaused();
                nextSongThread.onPause();
            } else {
                if (firstTime) {
                    playNextSong();
                } else if (player.getPlaybackState().positionMs < player.getMetadata().currentTrack.durationMs) {
                    player.resume(op);
                    songs.get(0).markPlaying();
                    nextSongThread.onResume();
                }
            }
        }
    }


    //plays the next song on our playlist
    public void playNextSong() {
        Log.d("Error", "blah");
        if(!songs.isEmpty()){
            Song currSong = songs.get(0);
            if(firstTime) {
                System.err.println("DEBUG: " + this);
                nextSongThread = new PlaylistThread(currSong.getDuration(), this);
                player.playUri(op, currSong.getUri(), 0, 0);
                nextSongThread.start();
                currSong.markPlaying();
                firstTime = false;
            }
            else{
                currSong.markFinishedPlaying();
                //remove(currSong);
                if(!songs.isEmpty()) {
                    Song nextSong =  songs.get(0);
                    nextSongThread = new PlaylistThread(nextSong.getDuration(), this);
                    player.playUri(op, nextSong.getUri(), 0, 0);
                    nextSongThread.start();
                    nextSong.markPlaying();
                }
            }
        }

    }

    //skips to next song
    public void skip(){
        Log.d("Debug", "" + songs.size());
        //Log.d("Debug", "" + Global.party.getPlaylist().songs.size());
        if(songs.size() > 1) {
            nextSongThread.interrupt();
        }
        else if(songs.size() == 1){
            songs.get(0).markFinishedPlaying();
            //remove(songs.get(0));
            player.pause(op);
            nextSongThread = null;
        }
    }

    public List<Song> reorder(int fromIndex, int toIndex){
        Song toMove = songs.remove(fromIndex);
        songs.add(toIndex, toMove);
        return songs;
    }

    public ListIterator<Song> listIterator(){
        songs = Global.party.getPlaylist().songs;
        return songs.listIterator();
    }

    public List<Song> getSongs(){ return songs;}

    public Song getCurrSong(){ return songs.get(0);}
}
