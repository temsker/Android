package com.tekapic;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.ByteArrayOutputStream;

public class ProfileActivity extends AppCompatActivity {


    private SharedPreferences.Editor editor;
    private SharedPreferences prefs;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final String EXTRA_MESSAGE = "com.tekapic.ALBUMS";


    public void showAlbums(View view) {

    }


    public void showAllPictures(View view) {
        //save showAllPictures as a string in intent
        //start intent PicturesActivity

        Intent intent = new Intent(this, PicturesActivity.class);
        intent.putExtra("show_all_pictures", "showAllPictures");
        startActivity(intent);
    }


    public void takeAPicture(View view) {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            imageBitmap.recycle();
            //Now the picture is in the variable: byteArray.

            openPostActivity(byteArray);



        }

    }

    public void openPostActivity(byte[] byteArray) {

        Intent intent = new Intent(this, PostActivity.class);
        intent.putExtra(EXTRA_MESSAGE, byteArray);
        startActivity(intent);

    }






    public void getAccountDetails(User user) {

        Intent intent = new Intent(this, EditProfileActivity.class);
        intent.putExtra("MyClass", user);
        startActivity(intent);

    }

    public void editProfile(View view) {

        new GetAccountDetailsFromServer(this, ConnectToServer.getDataOutputStream(),
                ConnectToServer.getObjectInputStream()).execute("getAccountDetails");
    }

    @Override
    public void onBackPressed() {
        //goes back to the loginActivity therefore need to logout so
        //send string "logOut" to server to logout.

        new SendStringToServer(ConnectToServer.getDataOutputStream()).execute("logOut");

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
