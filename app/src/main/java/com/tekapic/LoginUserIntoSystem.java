package com.tekapic;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by LEV on 12/04/2018.
 */

public class LoginUserIntoSystem extends AsyncTask<Login,Void,String> {

    private LoginActivity loginActivity;
    private Socket socket;

    public LoginUserIntoSystem(LoginActivity loginActivit, Socket socket) {
        this.loginActivity = loginActivity;
        this.socket = socket;
    }

    @Override
    protected String doInBackground(Login... voids) {

        Login login = voids[0];
        String dataFromServer = null;


        try {

            ObjectOutputStream outToServer =
                    new ObjectOutputStream(socket.getOutputStream() );

            BufferedReader inFromServer =
                    new BufferedReader(new
                            InputStreamReader(socket.getInputStream()));

            outToServer.writeObject(login);

            dataFromServer = inFromServer.readLine();



        } catch (IOException e) {
            e.printStackTrace();
        }

        return dataFromServer;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        loginActivity.checkIfUsernameExistsInDatabase(s);

    }
}