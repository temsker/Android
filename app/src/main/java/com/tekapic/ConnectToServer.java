package com.tekapic;

/**
 * Created by LEV on 15/04/2018.
 */

import android.os.AsyncTask;
import java.io.IOException;
import java.net.Socket;

public class ConnectToServer extends AsyncTask<Void,Void,Void> {

    private static Socket socket = null;


    public static Socket getSocket() {
        return socket;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        try {
            socket = new Socket("192.168.1.12", 10000);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


}