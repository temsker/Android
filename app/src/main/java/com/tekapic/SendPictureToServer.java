package com.tekapic;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Created by LEV on 23/05/2018.
 */

public class SendPictureToServer extends AsyncTask<Picture,Void,Void> {


    private ObjectOutputStream objectOutputStream = null; // send object to the server

    public SendPictureToServer(ObjectOutputStream objectOutputStream) {
        this.objectOutputStream = objectOutputStream;
    }

    @Override
    protected Void doInBackground(Picture... voids) {

        Picture picture = voids[0];
        try {

            objectOutputStream.writeObject(picture);


        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}