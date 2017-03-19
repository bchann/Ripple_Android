package com.example.brianchan.ripple_android;

import android.view.View;
import android.widget.Toast;

/**
 * Created by Parikshit on 3/5/17.
 */

public class PlaylistPresenter {

    PlaylistActivity activity;
    Playlist play;
    boolean paused = false;

    public PlaylistPresenter (PlaylistActivity activity){
        this.activity = activity;
    }

    public void onCreate(){}


    public void toggle(View view){
        play = Global.party.getPlaylist();
        if (play.songs != null && !play.getSongs().isEmpty()) {
            play.togglePlayPause();
            final BtnFonts play_pause = (BtnFonts) view.findViewById(R.id.playPause);
            play_pause.setTag(0);
            play_pause.setText(R.string.pause_icon);
            play_pause.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int status = (Integer) v.getTag();
                    if (status == 1) {
                        play = Global.party.getPlaylist();
                        play.togglePlayPause();
                        play_pause.setText(R.string.pause_icon);
                        v.setTag(0); //pause
                        System.err.println("hii");
                        paused = false;
                    } else {
                        play = Global.party.getPlaylist();
                        play.togglePlayPause();
                        play_pause.setText(R.string.play_icon);
                        v.setTag(1); //play
                        paused = true;
                    }
                }
            });
        }
        else {
            Toast toast = Toast.makeText(view.getContext(), "Populate the playlist first!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void skip(View view){
        play = Global.party.getPlaylist();
        if (!paused && Global.nextSongThread != null) {
            play = Global.party.getPlaylist();
            play.skip();
        }
        else {
            Toast toast = Toast.makeText(view.getContext(), "Can't skip while paused!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }


}
