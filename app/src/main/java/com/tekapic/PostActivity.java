package com.tekapic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import java.util.ArrayList;

public class PostActivity extends AppCompatActivity {

    CheckBox me, family, friends, love, pets, nature, sport;
    CheckBox persons, animals, cars, views, food;
    byte[] pictureInByteArray;



    public void post(View view) {

        ArrayList<String> albums = new ArrayList<String>();;

        if(me.isChecked() == true) {
            albums.add("Me");
        }
        if(family.isChecked() == true) {
            albums.add("Family");
        }
        if(friends.isChecked() == true) {
            albums.add("Friends");
        }
        if(love.isChecked() == true) {
            albums.add("Love");
        }
        if(pets.isChecked() == true) {
            albums.add("Pets");
        }
        if(nature.isChecked() == true) {
            albums.add("Nature");
        }
        if(sport.isChecked() == true) {
            albums.add("Sport");
        }
        if(persons.isChecked() == true) {
            albums.add("Persons");
        }
        if(animals.isChecked() == true) {
            albums.add("Animals");
        }
        if(cars.isChecked() == true) {
            albums.add("Cars");
        }
        if(views.isChecked() == true) {
            albums.add("Views");
        }
        if(food.isChecked() == true) {
            albums.add("Food");
        }
        
        if(albums.isEmpty()) {
            Toast.makeText(this, "Select at least one album.", Toast.LENGTH_SHORT).show();
            return;
        }

        Picture picture = new Picture(albums, pictureInByteArray, "23/05/2018");


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

        me = findViewById(R.id.checkBox_me);
        family = findViewById(R.id.checkBox_family);
        friends = findViewById(R.id.checkBox_friends);
        love = findViewById(R.id.checkBox_love);
        pets = findViewById(R.id.checkBox_pets);
        nature = findViewById(R.id.checkBox_nature);
        sport = findViewById(R.id.checkBox_sport);
        persons = findViewById(R.id.checkBox_persons);
        animals = findViewById(R.id.checkBox_animals);
        cars = findViewById(R.id.checkBox_cars);
        views = findViewById(R.id.checkBox_views);
        food = findViewById(R.id.checkBox_food);

        Intent intent = getIntent();
        pictureInByteArray = intent.getByteArrayExtra(ProfileActivity.EXTRA_MESSAGE);


    }
}
