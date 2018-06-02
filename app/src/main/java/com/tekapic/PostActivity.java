package com.tekapic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;


public class PostActivity extends AppCompatActivity {

    CheckBox me, family, friends, love, pets, nature, sport;
    CheckBox persons, animals, vehicles, views, food, things, funny;
    byte[] pictureInByteArray;
    CheckBox[] checkBoxesArray = new CheckBox[Picture.numberOfAlbums];
    boolean isButtonPostClicked;


    public void initialcheckBoxesArray() {
        checkBoxesArray[0] = me;
        checkBoxesArray[1] = family;
        checkBoxesArray[2] = friends;
        checkBoxesArray[3] = love;
        checkBoxesArray[4] = pets;
        checkBoxesArray[5] = nature;
        checkBoxesArray[6] = sport;
        checkBoxesArray[7] = persons;
        checkBoxesArray[8] = animals;
        checkBoxesArray[9] = vehicles;
        checkBoxesArray[10] = views;
        checkBoxesArray[11] = food;
        checkBoxesArray[12] = things;
        checkBoxesArray[13] = funny;
    }

    public void post(View view) {

        if(isButtonPostClicked) {
            return;
        }

        isButtonPostClicked = true;

        boolean albums[] = new boolean[Picture.numberOfAlbums];
        boolean userDidNotSelectAnyAlbum = true;

        for(int i = 0; i < checkBoxesArray.length; i++) {
            if(checkBoxesArray[i].isChecked()) {
                albums[i] = true;
            }
            else {
                albums[i] = false;
            }
        }

        for(int i = 0; i < checkBoxesArray.length; i++) {
            if(checkBoxesArray[i].isChecked()) {
                userDidNotSelectAnyAlbum = false;
               break;
            }
        }

        //If the user didn't check any album.
        if(userDidNotSelectAnyAlbum) {
            Toast.makeText(this, "Select at least one album.", Toast.LENGTH_SHORT).show();
            return;
        }

        Picture picture = new Picture(albums, pictureInByteArray, "30/05/2018");


        new SendStringToServer(ConnectToServer.getDataOutputStream()).execute("storePicture");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new SendPictureToServer(ConnectToServer.getObjectOutputStream()).execute(picture);

        Toast.makeText(this, "Your picture has been posted.", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        isButtonPostClicked = false;

        me = findViewById(R.id.checkBox_me);
        family = findViewById(R.id.checkBox_family);
        friends = findViewById(R.id.checkBox_friends);
        love = findViewById(R.id.checkBox_love);
        pets = findViewById(R.id.checkBox_pets);
        nature = findViewById(R.id.checkBox_nature);
        sport = findViewById(R.id.checkBox_sport);
        persons = findViewById(R.id.checkBox_persons);
        animals = findViewById(R.id.checkBox_animals);
        vehicles = findViewById(R.id.checkBox_vehicles);
        views = findViewById(R.id.checkBox_views);
        food = findViewById(R.id.checkBox_food);
        things = findViewById(R.id.checkBox_things);
        funny = findViewById(R.id.checkBox_funny);

        initialcheckBoxesArray();

        Intent intent = getIntent();
        pictureInByteArray = intent.getByteArrayExtra(ProfileActivity.EXTRA_MESSAGE);


    }
}
