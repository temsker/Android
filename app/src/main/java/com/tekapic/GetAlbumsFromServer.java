package com.tekapic;

import android.os.AsyncTask;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

/**
 * Created by LEV on 10/06/2018.
 */

public class GetAlbumsFromServer extends AsyncTask<String,Void,ArrayList<String>> {

    private AlbumsActivity albumsActivity;
    private static DataOutputStream dataOutputStream = null; // write string to the server
    private static ObjectInputStream objectInputStream = null; // read object from server

    public GetAlbumsFromServer(AlbumsActivity albumsActivity, DataOutputStream dataOutputStream,
                                 ObjectInputStream objectInputStream) {
        this.albumsActivity = albumsActivity;
        GetAlbumsFromServer.dataOutputStream = dataOutputStream;
        GetAlbumsFromServer.objectInputStream = objectInputStream;
    }


    @Override
    protected ArrayList<String> doInBackground(String... voids) {

        String requestOfAlbums = voids[0];
        ArrayList<String> albums = new ArrayList<String>();


        try {

            dataOutputStream.writeBytes(requestOfAlbums + '\n');

            albums = (ArrayList<String>) objectInputStream.readObject();



        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return albums;
    }

    @Override
    protected void onPostExecute(ArrayList<String> albums) {
        super.onPostExecute(albums);

        albumsActivity.getAlbums(albums);
    }
}
