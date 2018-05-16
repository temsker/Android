package com.tekapic;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {



    private EditText loginEmail;
    private EditText loginPassword;
    public static final String EXTRA_MESSAGE = "com.tekapic.EMAIL";
    public static boolean isProfileActivityCreatedBefore = false;

    public static final String USERS = "users";
    public static final String EMAIL ="email";
    public static final String PASSWORD ="password";
    private SharedPreferences.Editor editor;
    private SharedPreferences prefs;


    private void openProfileActivity() {
        Intent intent = new Intent(this, ProfileActivity.class);

        if(loginEmail.getText().toString().isEmpty()) {
            String restoredEmail = prefs.getString(EMAIL, null);
            intent.putExtra(EXTRA_MESSAGE, restoredEmail);

        }
        else {
            intent.putExtra(EXTRA_MESSAGE, loginEmail.getText().toString());
        }

        startActivity(intent);
    }


    private void makeAToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    public void checkIfUsernameExistsInDatabase(String resultFromServer) {

        if(resultFromServer == null) {
            popUpAlertDialogConnectionError();
            return;
        }

        // 0 - user doesn't exist in database
        if(resultFromServer.equals("0")) {
            makeAToast("Username doesn't exist.");
            return;
        }
        //1 - user exists in database, wrong password
        else if(resultFromServer.equals("1")) {
            makeAToast("Wrong password.");
            return;
        }
        // 2 - user exists in database, the password is correct,
        // open profile activity
        else if(resultFromServer.equals("2"))  {

            if(!loginEmail.getText().toString().isEmpty()) {
                editor.putString(EMAIL, loginEmail.getText().toString());
                editor.putString(PASSWORD, loginPassword.getText().toString());

                editor.commit();
            }

            openProfileActivity();

        }
    }



    public void passwordForgotten (View view) {

        return;

//        Intent intent = new Intent(this, PasswordForgotten.class);
//        intent.putExtra(EXTRA_MESSAGE, "");
//        startActivity(intent);
    }


    public void login(View view) {

        do {
            if(ConnectToServer.getSocket() == null || isNetworkConnected() == false) {
                popUpAlertDialogConnectionError();
            }
            if(ConnectToServer.getSocket() != null && isNetworkConnected() == true) {
                break;
            }
        }
        while (ConnectToServer.getSocket() == null || isNetworkConnected() == false);


        if(loginEmail.getText().toString().isEmpty() || loginPassword.getText().toString().isEmpty())
        {
            makeAToast("Please fill in all fields");
            return;
        }

        Login login = new Login(loginEmail.getText().toString(),
                loginPassword.getText().toString());


        new LoginUserIntoSystem(this, ConnectToServer.getObjectOutputStream(),
                ConnectToServer.getBufferedReader()).execute(login);


    }

    public void registration(View view) {

        do {
            if(ConnectToServer.getSocket() == null || isNetworkConnected() == false) {
                popUpAlertDialogConnectionError();
            }
            if(ConnectToServer.getSocket() != null && isNetworkConnected() == true) {
                break;
            }
        }
        while (ConnectToServer.getSocket() == null || isNetworkConnected() == false);


        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }

    private void connectToServer() {

        if(isNetworkConnected() == false) {
            popUpAlertDialogConnectionError();
            return;
        }

        new ConnectToServer().execute();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(ConnectToServer.getSocket() == null) {
            popUpAlertDialogConnectionError();
        }
    }


    private void popUpAlertDialogConnectionError() {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle("Error");
        builder1.setMessage("There might be problems with the server or network connection.");
        builder1.setCancelable(false);

        builder1.setPositiveButton(
                "TRY AGAIN",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if(ConnectToServer.getSocket() == null) {
                            connectToServer();
                            return;
                        }
                        if(isNetworkConnected() == false) {
                            popUpAlertDialogConnectionError();
                        }

                    }
                });

        AlertDialog alertDialog = builder1.create();
        alertDialog.show();
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //get
        editor = getSharedPreferences(USERS, MODE_PRIVATE).edit();

        //retrieve
        prefs = getSharedPreferences(USERS, MODE_PRIVATE);

        if(isProfileActivityCreatedBefore) {

            editor.putString(LoginActivity.EMAIL, null);
            editor.commit();
            //send string zero "0" as email to server to break the login loop.
            new LogoutUserFromSystem(ConnectToServer.getDataOutputStream()).execute();

            isProfileActivityCreatedBefore = false;

        }

        loginEmail = findViewById(R.id.login_email);
        loginPassword = findViewById(R.id.login_password);




        if(ConnectToServer.getSocket() == null) {
            connectToServer();
        }



        String restoredEmail = prefs.getString(EMAIL, null);
        String restoredPassword = prefs.getString(PASSWORD, null);

        if (restoredEmail != null && restoredPassword != null) {
            Login login = new Login(restoredEmail, restoredPassword);

            new LoginUserIntoSystem(this, ConnectToServer.getObjectOutputStream(),
                    ConnectToServer.getBufferedReader()).execute(login);

        }

    }

    //need to try sql injection.
    //maybe to add check input email format.


}
