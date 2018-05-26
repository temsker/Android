package com.tekapic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class EditProfileActivity extends AppCompatActivity {

    private TextView mobileNumber;
    private TextView email;
    private TextView username;
    private TextView fullName;
    private TextView password;
    private User user;

    public void setAccountDetails() {

        mobileNumber.setText(user.getMobileNumber());
        email.setText(user.getEmail());
        username.setText(user.getUsername());
        fullName.setText(user.getFullName());
        password.setText(user.getPassword());

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        mobileNumber = findViewById(R.id.txt_mobile_number);
        email = findViewById(R.id.txt_email);
        username = findViewById(R.id.txt_username);
        fullName = findViewById(R.id.txt_full_name);
        password = findViewById(R.id.txt_password);

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("MyClass");
        setAccountDetails();
    }
}
