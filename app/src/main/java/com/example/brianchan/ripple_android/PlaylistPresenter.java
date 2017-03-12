package com.example.brianchan.ripple_android;

import android.view.View;

import javax.microedition.khronos.opengles.GL;

/**
 * Created by Parikshit on 3/5/17.
 */

public class PlaylistPresenter {

    PlaylistActivity activity;
    Playlist play;

    public PlaylistPresenter (PlaylistActivity activity){
        this.activity = activity;
        //this.play = Global.party.getPlaylist();
    }

    public void onCreate(){

    }


    public void toggle(View view){
        System.out.println("DEBUG in PlaylistPresenter: " + Global.party.getPlaylist().songs.get(0).getDuration());
        play = Global.party.getPlaylist();
        play.togglePlayPause();
    }

    public void skip(View view){
        play = Global.party.getPlaylist();
        play.skip();
    }


}
