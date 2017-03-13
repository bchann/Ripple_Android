package com.example.brianchan.ripple_android;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Error;
import com.spotify.sdk.android.player.Player;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import static com.example.brianchan.ripple_android.Global.firstTime;
import static com.example.brianchan.ripple_android.Global.nextSongThread;
import static com.example.brianchan.ripple_android.Global.player;
import static com.google.firebase.database.FirebaseDatabase.getInstance;


/**
 * Created by Parikshit on 2/26/17.
 */

public class Playlist extends SongList {
    //private static Party party;

    private static Player.OperationCallback op;
    private static final FirebaseDatabase database = getInstance();
    private static final DatabaseReference songlistsRef = database.getReference("songlists");

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
        if (songs == null) {
            songs = new LinkedList<Song>();
        }
        songs.add(song);
        Global.party.setPlaylist(this);
    }

    public Song dequeue() {
        System.out.println("DEBUG: The song that dequeued was: " + songs.get(0).getTitle());
        System.out.println("DEBUG: The song that's first is: " + songs.get(0).getTitle());
        return songs.remove(0);
    }

    private void remove(Song song) {
        songs.remove(song);
    }

    public void togglePlayPause() {
        if (!songs.isEmpty()) {
            if (player.getPlaybackState().isPlaying) {
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
        if(!songs.isEmpty()){
            Song currSong = songs.get(0);
            System.out.println("Debug: The current song is: " +  songs.get(0).getTitle());
            if(firstTime) {
                nextSongThread = new PlaylistThread(currSong.getDuration(), this);
                player.playUri(op, currSong.getUri(), 0, 0);
                nextSongThread.start();
                currSong.markPlaying();
                firstTime = false;
            }
            else{
                System.out.println("DEBUG: The song that just finished is: " +  songs.get(0).getTitle());
                currSong.markFinishedPlaying();
                System.out.println("DEBUG: The song about to start is: " + songs.get(0).getTitle());
                remove(currSong);
                if(!songs.isEmpty()) {
                    Song nextSong =  songs.get(0);
                    System.out.println("DEBUG: The NEXT SONG IS: " + nextSong.getTitle());
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

    public void reorder(int fromIndex, int toIndex){
        Song from = songs.get(fromIndex);
        Song to = songs.get(toIndex);
        songs.set(fromIndex, to);
        songs.set(toIndex, from);
        Global.party.setPlaylist(this);
        songlistsRef.child(Party.playlist_id).setValue(this);
    }

    public ListIterator<Song> listIterator(){
        songs = Global.party.getPlaylist().songs;
        return songs.listIterator();
    }

    public List<Song> getSongs(){ return songs;}

    public Song getCurrSong(){ return songs.get(0);}
}
