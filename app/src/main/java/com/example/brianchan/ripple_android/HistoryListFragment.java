package com.example.brianchan.ripple_android;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Parikshit on 3/5/17.
 */

public class HistoryListFragment extends Fragment {

    private List<Song> songList = new LinkedList<>();

    public HistoryListFragment() {
    }

    public static HistoryListFragment newInstance() {
        return new HistoryListFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_historylist, container, false);
        Context ctx = getActivity();

        ListView listView = (ListView) rootView.findViewById(R.id.historyList);

        if (songList.size() != 0) {
            listView.setAdapter(new SongListItemAdapter(ctx, R.layout.song_view, songList));
        }

        return rootView;
    }
}
