package com.tekapic;

import android.os.AsyncTask;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by LEV on 03/05/2018.
 */

public class LogoutUserFromSystem extends AsyncTask<Void,Void,Void> {

    private static DataOutputStream dataOutputStream = null; // write string to the server

    public LogoutUserFromSystem(DataOutputStream dataOutputStream) {
        LogoutUserFromSystem.dataOutputStream = dataOutputStream;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        try {
            dataOutputStream.writeBytes("0" + '\n');
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
