package com.tekapic;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity {


    private SharedPreferences.Editor editor;
    private SharedPreferences prefs;


    public void getAccountDetails(User user) {

        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("MyClass", user);
        startActivity(intent);

    }

    public void showAccountDetails(View view) {

        new GetAccountDetailsFromServer(this, ConnectToServer.getDataOutputStream(),
                ConnectToServer.getObjectInputStream()).execute(LoginActivity.email);
    }

    @Override
    public void onBackPressed() {
        new LogoutUserFromSystem(ConnectToServer.getDataOutputStream()).execute();

        editor.putString(LoginActivity.EMAIL, null);
        editor.commit();

        super.onBackPressed();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //get
        editor = getSharedPreferences(LoginActivity.USERS, MODE_PRIVATE).edit();

        //retrieve
        prefs = getSharedPreferences(LoginActivity.USERS, MODE_PRIVATE);

        LoginActivity.isProfileActivityCreatedBefore = true;

//        Intent intent = getIntent();
//        email = intent.getStringExtra(LoginActivity.EXTRA_MESSAGE);


        // * need to check if there is net connection.

    }
}
