package com.tekapic;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by LEV on 12/04/2018.
 */

public class RegisterNewUser extends AsyncTask<User,Void,String> {


    private Socket socket;
    RegistrationActivity registrationActivity;

    public RegisterNewUser(Socket socket, RegistrationActivity registrationActivity) {
        this.socket = socket;
        this.registrationActivity = registrationActivity;
    }

    @Override
    protected String doInBackground(User... voids) {

        User user = voids[0];
        String dataFromServer = null;
        try {

            ObjectOutputStream  outToServerObject =
                    new ObjectOutputStream(socket.getOutputStream() );

            BufferedReader inFromServer =
                    new BufferedReader(new
                            InputStreamReader(ConnectToServer.getSocket().getInputStream()));


            outToServerObject.writeObject(user);
            dataFromServer = inFromServer.readLine();



        } catch (IOException e) {
            e.printStackTrace();
        }

        return dataFromServer;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        registrationActivity.checkRegistrationProcedure(s);
    }
}