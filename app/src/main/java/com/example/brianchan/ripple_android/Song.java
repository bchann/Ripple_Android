package com.example.brianchan.ripple_android;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.brianchan.ripple_android.Collaborator;
import com.example.brianchan.ripple_android.Global;
import com.example.brianchan.ripple_android.History;

import org.json.JSONObject;

/**
 * Created by rishi on 2/26/17.
 */

public class Song {
    public static final String REQUESTED = "requested";
    public static final String ACCEPTED = "accepted";
    public static final String REJECTED = "rejected";
    public static final String PLAYING = "playing";
    public static final String PAUSED = "paused";
    public static final String SKIPPED = "skipped";
    public static final String FINISHED_PLAYING = "finished playing";

    private String songId;
    private String title;
    private String uri;
    private long durationMs;
    private History history;
    private Playlist playlist;
    private Requests requests;
    private Party2 party2;
    private Collaborator collaborator;

    private JsonObjectRequest jsonRequest;

    public Song(String songId, Party2 party2, Collaborator collaborator) {
        this.songId = songId;
        this.party2 = party2;
        this.playlist = party2.getPlaylist();
        this.requests = party2.getRequests();
        this.history = party2.getHistory();
        this.collaborator = collaborator;

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
        //TODO update playlist by adding song
        //TODO update requests by removing request
        //TODO update status to ACCEPTED
    }

    public void reject(){
        requests.pop();
        //TODO update requests by removing request
        //TODO update status to REJECTED
    }

    public void markFinishedPlaying() {
        history.append(this);
        //TODO update playlist by removing song
        //TODO update history by adding song
        //TODO update status to FINISHED_PLAYING
    }

    public void markPlaying() {
        //TODO update status on firebase to PLAYING
    }

    public void markPaused() {
        //TODO update status on firebase to PAUSED
    }

}


