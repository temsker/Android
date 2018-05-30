package com.tekapic;

import java.io.Serializable;


/**
 * Created by LEV on 23/05/2018.
 */

public class Picture implements Serializable {

    public static final int numberOfAlbums = 13;

    private boolean albums[];
    private byte[] pictureInByteArray;
    private String date;



    public Picture() {
        albums = new boolean[numberOfAlbums];
        for(int i = 0; i < albums.length; i++) {
            albums[i] = false;
        }
        pictureInByteArray = null; // neeed to initialize with some picture
        date = "none";
    }

    public Picture(boolean[] albums, byte[] pictureInByteArray, String date) {
        this.albums = albums;
        this.pictureInByteArray = pictureInByteArray;
        this.date = date;
    }

    public boolean[] getAlbums() {
        return albums;
    }

    public void setAlbums(boolean[] albums) {
        this.albums = albums;
    }

    public byte[] getPictureInByteArray() {
        return pictureInByteArray;
    }

    public void setPictureInByteArray(byte[] pictureInByteArray) {
        this.pictureInByteArray = pictureInByteArray;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
