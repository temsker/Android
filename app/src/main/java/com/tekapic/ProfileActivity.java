package com.tekapic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

//    private TextView mobileNumber;
//    private TextView email;
//    private TextView username;
//    private TextView fullName;
//    private TextView password;

     TextView mobileNumber;
     TextView email;
     TextView username;
     TextView fullName;
     TextView password;


    public void setUserData(User user) {

        mobileNumber.setText(user.getMobileNumber());
        email.setText(user.getEmail());
        username.setText(user.getUsername());
        fullName.setText(user.getFullName());
        password.setText(user.getPassword());

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mobileNumber = findViewById(R.id.txt_mobile_number);
        email = findViewById(R.id.txt_email);
        username = findViewById(R.id.txt_username);
        fullName = findViewById(R.id.txt_full_name);
        password = findViewById(R.id.txt_password);



        LoginActivity.isProfileActivityCreatedBefore = true;

        Intent intent = getIntent();
        String email = intent.getStringExtra(LoginActivity.EXTRA_MESSAGE);

        new GetDataFromServer(this, ConnectToServer.getDataOutputStream(),
                ConnectToServer.getObjectInputStream()).execute(email);


        //1.send email(string) to server

        //2. get the user(object) from server.

        //3. set all the data of the user object of this layout.


        // * need to check if there is net connection.

    }
}
