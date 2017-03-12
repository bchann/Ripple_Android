package com.example.brianchan.ripple_android;

import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
import java.util.List;

import static com.google.firebase.database.FirebaseDatabase.getInstance;

/**
 * Created by Parikshit on 3/5/17.
 */

public class RequestsFragment extends Fragment {
    TextView songnameView, authorView, albumView;
    ImageView image;
    private Requests requests = new Requests(Global.party);
    private Context ctx;
    private final FirebaseDatabase database = getInstance();
    private final DatabaseReference songlistsRef = database.getReference("songlists");
    private List<Song> songList = new LinkedList<>();

    public RequestsFragment() {}

    public static RequestsFragment newInstance() {
        return new RequestsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_request, container, false);
        ctx = getActivity();

        songnameView = (TextView) rootView.findViewById(R.id.songRequest);
        authorView = (TextView) rootView.findViewById(R.id.authorRequest);
        albumView = (TextView) rootView.findViewById(R.id.albumRequest);
        image = (ImageView) rootView.findViewById(R.id.albumArt);

        Global.rctx = ctx;
        Global.rrootView = rootView;

        //updateFields();

        FloatingActionButton reject = (FloatingActionButton) rootView.findViewById(R.id.rejectButton);
        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requests = Global.party.getRequests();
                if (!requests.isEmpty()) {
                    Song song = Global.party.getRequests().peek();
                    song.accept();
                    Toast.makeText(ctx, song.getTitle() + " Accepted!", Toast.LENGTH_SHORT).show();
                }
                updateFields();
            }
        });

        FloatingActionButton accept = (FloatingActionButton) rootView.findViewById(R.id.acceptButton);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requests = Global.party.getRequests();
                if (!requests.isEmpty()) {
                    Song song = Global.party.getRequests().peek();
                    song.accept();
                    Toast.makeText(ctx, song.getTitle() + " Rejected.", Toast.LENGTH_SHORT).show();
                }
                updateFields();
            }
        });

        DatabaseReference songsRef = database.getReference("songlists");
        songsRef.child(Party.request_list_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Global.party.setRequests(dataSnapshot.getValue(Requests.class));
                requests = Global.party.getRequests();
                songList = Global.party.getRequests().songs;
                if (songList != null) {
                    /*for (Song song: songList){
                        song.getData();
                    }*/
                    songList.get(0).getData();
                }
                updateFields();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        return rootView;
    }

    private void updateFields() {
        requests = Global.party.getRequests();
        if (requests.isEmpty()) {
            songnameView.setText("No More Requests :(");
            authorView.setText("");
            albumView.setText("");
            image.setImageResource(android.R.color.transparent);
        }
        else {
            Song topRequest = requests.peek();
            songnameView.setText(topRequest.getTitle());
            authorView.setText(topRequest.getArtist());
            albumView.setText(topRequest.getAlbumTitle());
            new DownloadImageTask((ImageView) Global.rrootView.findViewById(R.id.albumArt))
                    .execute(topRequest.getMedImageURI());
        }
    }
}
