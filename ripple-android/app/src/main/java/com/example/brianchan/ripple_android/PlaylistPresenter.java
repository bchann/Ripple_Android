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
        //this.play = Global.party.getPlaylist();
    }

    public void onCreate(){

    }


    public void toggle(View view){
        play = Global.party.getPlaylist();
        play.togglePlayPause();
        final BtnFonts play_pause = (BtnFonts) view.findViewById(R.id.playPause);
        play_pause.setTag(0);
        play_pause.setText(R.string.pause_icon);
        play_pause.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                final int status =(Integer) v.getTag();
                if(status == 1) {
                    play = Global.party.getPlaylist();
                    play.togglePlayPause();
                    play_pause.setText(R.string.pause_icon);
                    v.setTag(0); //pause
                } else {
                    play = Global.party.getPlaylist();
                    play.togglePlayPause();
                    play_pause.setText(R.string.play_icon);
                    v.setTag(1); //play
                }
            }
        });

    }

    public void skip(View view){
        play = Global.party.getPlaylist();
        play.skip();
    }


}
