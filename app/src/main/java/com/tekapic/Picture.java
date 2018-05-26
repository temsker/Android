package com.tekapic;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by LEV on 23/05/2018.
 */

public class Picture implements Serializable {

    private ArrayList<String> albums;
    private byte[] pictureInByteArray;
    private String date;

    public Picture() {
        this.albums = new ArrayList<String>();
        this.pictureInByteArray = null;
        this.date = "none";
    }

    public Picture(ArrayList<String> albums, byte[] pictureInByteArray, String date) {
        this.albums = albums;
        this.pictureInByteArray = pictureInByteArray;
        this.date = date;
    }

    public ArrayList<String> getAlbums() {
        return albums;
    }

    public void setAlbums(ArrayList<String> albums) {
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
