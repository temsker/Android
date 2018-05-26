package com.tekapic;

import android.os.AsyncTask;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Created by LEV on 03/05/2018.
 */

public class GetAccountDetailsFromServer extends AsyncTask<String,Void,User> {

    private ProfileActivity profileActivity;
    private static DataOutputStream dataOutputStream = null; // write string to the server
    private static ObjectInputStream objectInputStream = null; // read object from server


    public GetAccountDetailsFromServer(ProfileActivity profileActivity, DataOutputStream dataOutputStream,
                                       ObjectInputStream objectInputStream ) {
        this.profileActivity = profileActivity;
        GetAccountDetailsFromServer.dataOutputStream = dataOutputStream;
        GetAccountDetailsFromServer.objectInputStream = objectInputStream;
    }

    @Override
    protected User doInBackground(String... strings) {

        String string = strings[0];
        Object object;
        User user = new User();


        try {
            dataOutputStream.writeBytes(string + '\n');
            try {
                object = objectInputStream.readObject();

                if(object instanceof User) {
                    user = (User) object;

                }

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return user;
    }

    @Override
    protected void onPostExecute(User user) {
        super.onPostExecute(user);

        profileActivity.getAccountDetails(user);

    }
}
