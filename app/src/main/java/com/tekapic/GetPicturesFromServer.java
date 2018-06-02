package com.tekapic;

import android.os.AsyncTask;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

/**
 * Created by LEV on 02/06/2018.
 */

public class GetPicturesFromServer extends AsyncTask<String,Void,ArrayList<Picture>> {


    private PicturesActivity picturesActivity;
    private static DataOutputStream dataOutputStream = null; // write string to the server
    private static ObjectInputStream objectInputStream = null; // read object from server

    public GetPicturesFromServer(PicturesActivity picturesActivity, DataOutputStream dataOutputStream,
                                 ObjectInputStream objectInputStream) {
        this.picturesActivity = picturesActivity;
        GetPicturesFromServer.dataOutputStream = dataOutputStream;
        GetPicturesFromServer.objectInputStream = objectInputStream;
    }


    @Override
    protected ArrayList<Picture> doInBackground(String... voids) {

        String requestOfPictures = voids[0];
        ArrayList<Picture> pictures = new ArrayList<Picture>();


        try {

            dataOutputStream.writeBytes(requestOfPictures + '\n');

            pictures = (ArrayList<Picture>) objectInputStream.readObject();



        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return pictures;
    }

    @Override
    protected void onPostExecute(ArrayList<Picture> pictures) {
        super.onPostExecute(pictures);

        picturesActivity.getAllPictures(pictures);
    }
}