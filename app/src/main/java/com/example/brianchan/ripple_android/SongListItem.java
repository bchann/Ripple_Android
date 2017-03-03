package com.example.brianchan.ripple_android;

/**
 * Created by Brian Chan on 3/3/2017.
 */

public class SongListItem {
    private String name;
    private String spotify_id;
    private String image;

    public SongListItem(String name, String spotify_id, String image) {
        this.name = name;
        this.spotify_id = spotify_id;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpotify_id() {
        return spotify_id;
    }

    public void setSpotify_id(String spotify_id) {
        this.spotify_id = spotify_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
