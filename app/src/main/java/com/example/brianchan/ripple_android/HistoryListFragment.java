package com.example.brianchan.ripple_android;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
import java.util.List;

import static com.google.firebase.database.FirebaseDatabase.getInstance;

/**
 * Created by Brian on 3/5/17.
 */

public class HistoryListFragment extends Fragment {
    private List<Song> songList = new LinkedList<>();
    private ListView listView;
    private Context ctx;
    private final FirebaseDatabase database = getInstance();

    public HistoryListFragment() {}

    public static HistoryListFragment newInstance() {
        return new HistoryListFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_historylist, container, false);
        listView = (ListView) rootView.findViewById(R.id.historyList);
        ctx = getActivity();

        DatabaseReference songsRef = database.getReference("songlists");
        songsRef.child(Party.history_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Global.party.setHistory(dataSnapshot.getValue(History.class));
                songList = Global.party.getHistory().songs;

                if (songList == null) {
                    listView.setAdapter(new SongListItemAdapter(ctx, R.layout.song_view, new LinkedList(), false));
                }
                else {
                    ListView playlistView = (ListView) rootView.findViewById(R.id.historyList);
                    playlistView.setAdapter(new SongListItemAdapter(ctx, R.layout.song_view, songList, false));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        return rootView;
    }
}
