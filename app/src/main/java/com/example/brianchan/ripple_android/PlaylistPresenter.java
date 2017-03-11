package com.example.brianchan.ripple_android;

import android.view.View;

/**
 * Created by Parikshit on 3/5/17.
 */

public class PlaylistPresenter {

    PlaylistActivity activity;
    Playlist play;

    public PlaylistPresenter (PlaylistActivity activity){
        this.activity = activity;
        this.play = Global.party.getPlaylist();
    }

    public void onCreate(){

    }


    public void toggle(View view){
        play.togglePlayPause();
    }


}
