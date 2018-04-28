package com.tekapic;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegistrationActivity extends AppCompatActivity {

    EditText mobileNumber, email, username, fullName, password;


    public void checkRegistrationProcedure(String resultFromServer) {

        if(resultFromServer == null) {
            popUpAlertDialogConnectionError();
            return;
        }

        // 0 - moblie number is already taken
        if(resultFromServer.equals("0")) {
            makeAToast("Mobile number is already taken.");
            return;
        }
        //1 - email is already taken
        else if(resultFromServer.equals("1")) {
            makeAToast("Email is already taken.");
            return;
        }
        // 2 - username is already taken
        else if(resultFromServer.equals("2"))  {
            makeAToast("Username is already taken.");
            return;
        }

        // 3 - mobile, email and username are available, data registered successfully
        // open LoginActivity
        else if(resultFromServer.equals("3")) {
            makeAToast("You have successfully registered.");
            openLoginActivity();
        }

    }


    public void makeAToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void openLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }


    public  boolean isValidEmailAddress() {

        Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(email.getText().toString());

        if(matcher.find() == false) {
            makeAToast("Enter a correct Email Address.");
            return false;
        }

        return true;
    }

    public boolean checkInput() {

        // check if the fields are not empty
        if(mobileNumber.getText().length() == 0 || email.getText().length() == 0 ||
                username.getText().length() == 0 || fullName.getText().length() == 0 ||
                password.getText().length() == 0) {

            makeAToast("Please fill in all fields");
            return true;
        }

        //check if the number of the digits is 10
        if(mobileNumber.getText().length() != 10) {
            makeAToast("Wrong mobile number");
            return true;
        }

        //check if the email address is correct
        if(isValidEmailAddress() == false) {
            return true;
        }

        //check if the username contains only letters, digits, underscores
        if(username.getText().toString().equals("_")) {
            makeAToast("Wrong username");
            return true;
        }
        else {
            if( !(username.getText().toString().toLowerCase().matches("[a-z0-9_]*")) ) {
                makeAToast("The username can contain onlty letters, digits, _");
                return true;
            }
        }

        //check if the full name contains only letters
        if(fullName.getText().toString().equals(" ")) {
            makeAToast("Wrong full name");
            return true;
        }
        else {
            if( !(fullName.getText().toString().matches("[a-zA-Z ]*")) ) {
                makeAToast("Wrong full name");
                return true;
            }
        }

        //check the password length
        if(password.getText().length() < 6) {
            makeAToast("Password must to contain at least 6 characters.");
            return true;
        }


        return false;
    }

    public void register(View view) {

        do {
            if(ConnectToServer.getSocket() == null || isNetworkConnected() == false) {
                popUpAlertDialogConnectionError();
            }
            if(ConnectToServer.getSocket() != null && isNetworkConnected() == true) {
                break;
            }
        }
        while (ConnectToServer.getSocket() == null || isNetworkConnected() == false);


        //check the input of the fields
        if(checkInput()) {
            //wrong input, stop the method execution
            return;
        }

        User user = new User();

        user.setMobileNumber(mobileNumber.getText().toString());
        user.setEmail(email.getText().toString());
        user.setUsername(username.getText().toString());
        user.setFullName(fullName.getText().toString());
        user.setPassword(password.getText().toString());


        //send the data of the new user to the server for registration
        new RegisterNewUser(ConnectToServer.getSocket(), this).execute(user);

    }

    public void popUpAlertDialogConnectionError() {

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

    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
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

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

//        if(ConnectToServer.getSocket() != null) {
//            Thread myThread = new Thread(new MyServerThread());
//            myThread.start();
//        }


        mobileNumber = findViewById(R.id.mobile_number);
        email = findViewById(R.id.email);
        username = findViewById(R.id.username);
        fullName = findViewById(R.id.full_name);
        password = findViewById(R.id.password);

    }



   // server listener

    class MyServerThread implements Runnable {

        Handler handler = new Handler();
        String message = null;

        @Override
        public void run() {

            try {

                while (true) {

                    BufferedReader inFromServer =
                            new BufferedReader(new
                                    InputStreamReader(ConnectToServer.getSocket().getInputStream()));

                    message = inFromServer.readLine();
                    if(message != null) {
                        if(message.contains("3")) {
                            message = "3";
                        }
                        Log.i("from server", message);

                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                            checkRegistrationProcedure(message);
                        }
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }





}
