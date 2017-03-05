package com.example.brianchan.ripple_android;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONObject;

import static com.google.firebase.database.FirebaseDatabase.getInstance;

/**
 * Created by rishi on 2/26/17.
 */

public class Song {
    private static final String VALID = "valid";
    private static final String REQUESTED = "requested";
    private static final String ACCEPTED = "accepted";
    private static final String REJECTED = "rejected";
    private static final String PLAYING = "playing";
    private static final String PAUSED = "paused";
    private static final String SKIPPED = "skipped";
    private static final String FINISHED_PLAYING = "finished playing";
    private static final String CHILD = "songs";

    public String songId;
    public String requester;
    public String status = VALID;

    private String title;
    private String uri;
    private long durationMs;
    private History history;
    private Playlist playlist;
    private Requests requests;
    private Party party;
    private Collaborator collaborator;
    private JsonObjectRequest jsonRequest;

    private final FirebaseDatabase database = getInstance();
    DatabaseReference songlistsRef = database.getReference("songlists");

    public Song(String songId, Party party, Collaborator collaborator) {
        this.songId = songId;
        this.party = party;

        this.playlist = party.getPlaylist();
        this.requests = party.getRequests();
        this.history = party.getHistory();

        this.collaborator = collaborator;

        this.requester = collaborator.getId();

        getData();
    }

    private void getData() {
        String url = "https://api.spotify.com/v1/tracks/" + songId;

        jsonRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            title = response.getString("name");
                            uri = response.getString("uri");
                            durationMs = response.getLong("duration_ms");

                        } catch (Exception e) {
                            Log.e("error", "error parsing data");
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("error", "error getting data");
                    }
                });

        Global.httpRequestQueue.add(jsonRequest);
    }

    public boolean finishedFetchingData() {
        return jsonRequest.hasHadResponseDelivered();
    }

    public String getTitle() {
        return title;
    }

    public String getUri() {
        return uri;
    }

    public long getDuration() {
        return durationMs;
    }

    public void accept() {
        playlist.enqueue(this);
        requests.pop();
        status = ACCEPTED;

        songlistsRef.child(CHILD).setValue(playlist);
        songlistsRef.child(CHILD).setValue(requests);
    }

    public void reject(){
        requests.pop();
        status = REJECTED;

        songlistsRef.child(CHILD).setValue(requests);
    }

    public void markFinishedPlaying() {
        history.append(this);
        playlist.dequeue();
        status = FINISHED_PLAYING;

        songlistsRef.child(CHILD).setValue(history);
        songlistsRef.child(CHILD).setValue(playlist);
    }

    public void markPlaying() {
        status = PLAYING;
        songlistsRef.child(CHILD).setValue(playlist);
    }

    public void markPaused() {
        status = PAUSED;
        songlistsRef.child(CHILD).setValue(playlist);
    }

}


