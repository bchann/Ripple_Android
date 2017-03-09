package com.example.brianchan.ripple_android;

import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
    private String albumTitle;
    private String artist;

    private String smImageURI;
    private String medImageURI;
    private String lgImageURI;

    private History history;
    private Playlist playlist;
    private Requests requests;
    private Party party;
    private Collaborator collaborator;

    private JsonObjectRequest jsonRequest;

    private static final FirebaseDatabase database = getInstance();
    private static final DatabaseReference songlistsRef = database.getReference("songlists");

    Song() {}

    Song(String songId, String requester, String status) {
        this.songId = songId;
        this.requester = requester;
        this.status = status;
    }

    public Song(String songId, Party party, Collaborator collaborator) {
        this.songId = songId;
        this.party = party;
        this.playlist = party.getPlaylist();
        this.requests = party.getRequests();
        this.history = party.getHistory();
        this.collaborator = collaborator;

        getData();
    }

    public void getData() {
        String url = "https://api.spotify.com/v1/tracks/" + songId;

        jsonRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            title = response.getString("name");
                            uri = response.getString("uri");
                            durationMs = response.getLong("duration_ms");

                            System.err.println(title);

                            String albumType = response.getJSONObject("album").getString("album_type");

                            if (albumType.equals("single")) {
                                albumTitle = title;
                            } else {
                                albumTitle = response.getJSONObject("album").getString("name");
                            }

                            JSONArray imageObjects = response.getJSONObject("album").getJSONArray("images");

                            smImageURI = imageObjects.getJSONObject(2).getString("url");
                            medImageURI = imageObjects.getJSONObject(1).getString("url");
                            lgImageURI = imageObjects.getJSONObject(0).getString("url");

                            ArrayList<String> artists = new ArrayList<>();

                            JSONArray artistObjects = response.getJSONArray("artists");

                            for (int i = 0; i < artistObjects.length(); i++) {
                                artists.add(artistObjects.getJSONObject(i).getString("name"));
                            }

                            artist = artists.get(0);

                            updateFields();

                            ListView playlistView = (ListView) Global.prootView.findViewById(R.id.playlist);
                            List<Song> playlistSongs = Global.party.getPlaylist().songs;
                            if (playlistSongs != null) {
                                playlistView.setAdapter(new SongListItemAdapter(Global.pctx, R.layout.song_view, playlistSongs));
                            }
                            else {
                                playlistView.setAdapter(new SongListItemAdapter(Global.pctx, R.layout.song_view, new LinkedList()));
                            }

                            ListView historyView = (ListView) Global.hrootView.findViewById(R.id.historyList);
                            List<Song> historySongs = Global.party.getHistory().songs;
                            if (historySongs != null) {
                                historyView.setAdapter(new SongListItemAdapter(Global.hctx, R.layout.song_view, historySongs));
                            }
                            else {
                                historyView.setAdapter(new SongListItemAdapter(Global.hctx, R.layout.song_view, new LinkedList()));
                            }

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

    private void updateFields() {
        System.err.println("Called");
        requests = Global.party.getRequests();
        TextView songnameView = (TextView) Global.rrootView.findViewById(R.id.songRequest);
        TextView authorView = (TextView) Global.rrootView.findViewById(R.id.authorRequest);
        TextView albumView = (TextView) Global.rrootView.findViewById(R.id.albumRequest);

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
            new DownloadImageTask((ImageView) Global.rrootView.findViewById(R.id.albumArt))
                    .execute(topRequest.getMedImageURI());
        }
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

    public String getAlbumTitle() {
        return albumTitle;
    }

    public String getArtist(){
        return artist;
    }

    public void accept() {
        requests = Global.party.getRequests();
        playlist = Global.party.getPlaylist();

        status = ACCEPTED;
        playlist.enqueue(new Song(this.songId, this.requester, status));
        requests.pop();

        Global.party.setRequests(requests);
        Global.party.setPlaylist(playlist);

        //songlistsRef.child(Party.playlist_id).setValue(playlist);
        //songlistsRef.child(Party.request_list_id).setValue(requests);

    }

    public void reject(){
        requests = Global.party.getRequests();
        requests.pop();
        status = REJECTED;

        Global.party.setRequests(requests);

        //songlistsRef.child(Global.party.request_list_id).setValue(requests);
    }

    public void markFinishedPlaying() {
        history = Global.party.getHistory();
        playlist = Global.party.getPlaylist();

        history.append(this);
        playlist.dequeue();
        status = FINISHED_PLAYING;

        Global.party.setHistory(history);
        Global.party.setPlaylist(playlist);

        //songlistsRef.child(Party.history_id).setValue(history);
        //songlistsRef.child(Party.playlist_id).setValue(playlist);
    }

    public void markPlaying() {
        playlist = Global.party.getPlaylist();
        status = PLAYING;
        Global.party.setPlaylist(playlist);

        //songlistsRef.child(Party.playlist_id).setValue(playlist);
    }

    public void markPaused() {

        playlist = Global.party.getPlaylist();
        status = PAUSED;
        Global.party.setPlaylist(playlist);

        //songlistsRef.child(Party.playlist_id).setValue(playlist);
    }

    public String getSmImageURI() {
        return smImageURI;
    }

    public String getMedImageURI() {
        return medImageURI;
    }

    public String getLgImageURI() {
        return lgImageURI;
    }

}


