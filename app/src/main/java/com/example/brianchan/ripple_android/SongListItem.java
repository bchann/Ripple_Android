package com.example.brianchan.ripple_android;

/**
 * Created by Brian Chan on 3/3/2017.
 */

public class SongListItem {
    private String name;
    private String author;
    private String image;
    private String album;

    public SongListItem(String name, String author, String image, String album) {
        this.name = name;
        this.author = author;
        this.image = image;
        this.album = album;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }
}
