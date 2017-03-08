package com.example.brianchan.ripple_android;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Parikshit on 3/5/17.
 */

public class RequestsFragment extends Fragment {
    TextView songnameView, authorView, albumView;
    private Requests requests = new Requests(Global.party);

    public RequestsFragment() {}

    public static RequestsFragment newInstance() {
        return new RequestsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_request, container, false);

        //TODO: Firebase listener

        songnameView = (TextView) rootView.findViewById(R.id.songRequest);
        authorView = (TextView) rootView.findViewById(R.id.authorRequest);
        albumView = (TextView) rootView.findViewById(R.id.albumRequest);

        updateFields();

        FloatingActionButton reject = (FloatingActionButton) rootView.findViewById(R.id.rejectButton);
        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requests.pop();
                updateFields();
            }
        });

        FloatingActionButton accept = (FloatingActionButton) rootView.findViewById(R.id.acceptButton);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requests.pop();
                updateFields();
            }
        });

        return rootView;
    }

    private void updateFields() {
        if (requests.isEmpty()) {
            songnameView.setText("No More Requests :(");
            authorView.setText("");
            albumView.setText("");
        }
        else {
            Song topRequest = requests.peek();
            songnameView.setText(topRequest.getTitle());
            authorView.setText(topRequest.getArtist());
            albumView.setText(topRequest.getAlbumTitle());
        }
    }
}
