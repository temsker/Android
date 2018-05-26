package com.tekapic;

import android.os.AsyncTask;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by LEV on 03/05/2018.
 */

public class SendStringToServer extends AsyncTask<String,Void,Void> {

    private static DataOutputStream dataOutputStream = null; // write string to the server

    public SendStringToServer(DataOutputStream dataOutputStream) {
        SendStringToServer.dataOutputStream = dataOutputStream;
    }

    @Override
    protected Void doInBackground(String... voids) {
        String string = voids[0];

        try {
            dataOutputStream.writeBytes(string + '\n');
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
