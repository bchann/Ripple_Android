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
 * Created by Brian on 3/7/17.
 */

public class PlaylistFragment extends Fragment {
    private List<Song> songList = new LinkedList<>();
    private ListView listView;
    private Context ctx;
    private final FirebaseDatabase database = getInstance();

    public PlaylistFragment() {}

    public static PlaylistFragment newInstance() {
        return new PlaylistFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_playlist, container, false);
        ctx = getActivity();
        listView = (ListView) rootView.findViewById(R.id.playlist);

        Global.pctx = ctx;
        Global.prootView = rootView;

        DatabaseReference songsRef = database.getReference("songlists");
        //playlist
        songsRef.child(Party.playlist_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Global.party.setPlaylist(dataSnapshot.getValue(Playlist.class));
                if ((Global.party != null && Global.party.getPlaylist() != null)){
                    songList = Global.party.getPlaylist().songs;
                    if (songList != null) {
                        for (Song song : songList) {
                            song.getData();
                        }
                    } else {
                        listView.setAdapter(new SongListItemAdapter(ctx, R.layout.song_view, new LinkedList()));
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        return rootView;
    }

    public void toggle(View view) {
        Global.party.getPlaylist().togglePlayPause();
    }
}
