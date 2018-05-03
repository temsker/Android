package com.tekapic;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Created by LEV on 12/04/2018.
 */

public class LoginUserIntoSystem extends AsyncTask<Login,Void,String> {

    private LoginActivity loginActivity;
    private ObjectOutputStream objectOutputStream = null; // send object to the server
    private BufferedReader bufferedReader = null; // get string from the server

    public LoginUserIntoSystem(LoginActivity loginActivity, ObjectOutputStream objectOutputStream,
                               BufferedReader bufferedReader) {
        this.loginActivity = loginActivity;
        this.objectOutputStream = objectOutputStream;
        this.bufferedReader = bufferedReader;
    }

    @Override
    protected String doInBackground(Login... voids) {

        Login login = voids[0];
        String dataFromServer = null;

        try {

            objectOutputStream.writeObject(login);

            dataFromServer = bufferedReader.readLine();


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