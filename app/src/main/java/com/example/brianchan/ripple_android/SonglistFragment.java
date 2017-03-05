package com.example.brianchan.ripple_android;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Parikshit on 3/5/17.
 */

public class SonglistFragment extends Fragment {

    private ArrayList<Song> songList;

    public SonglistFragment(ArrayList<Song> songList) {
        this.songList = songList;
    }

    public static SonglistFragment newInstance(ArrayList<Song> songList) {
        SonglistFragment fragment = new SonglistFragment(songList);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_songlist, container, false);

        Context ctx = getActivity();


        ListView listView = (ListView) rootView.findViewById(R.id.historyList);
        listView.setAdapter(new SongListAdapter(ctx, R.layout.song_view, songList));

        return rootView;
    }
}
