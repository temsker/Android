package com.tekapic;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Created by LEV on 12/04/2018.
 */

public class RegisterNewUser extends AsyncTask<User,Void,String> {


    private RegistrationActivity registrationActivity;
    private ObjectOutputStream objectOutputStream = null; // send object to the server
    private BufferedReader bufferedReader = null; // get string from the server

    public RegisterNewUser(RegistrationActivity registrationActivity, ObjectOutputStream objectOutputStream,
                           BufferedReader bufferedReader) {
        this.registrationActivity = registrationActivity;
        this.objectOutputStream = objectOutputStream;
        this.bufferedReader = bufferedReader;
    }

    @Override
    protected String doInBackground(User... voids) {

        User user = voids[0];
        String dataFromServer = null;
        try {

            objectOutputStream.writeObject(user);
            dataFromServer = bufferedReader.readLine();


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