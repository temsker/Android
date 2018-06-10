package com.tekapic;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class PicturesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MyRecyclerViewAdapter adapter;
    TextView posts;



    public void getAllPictures(ArrayList<Picture> pictures) {

        posts.setText("posts: " + Integer.toString(pictures.size()));
        adapter = new MyRecyclerViewAdapter(pictures, this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pictures);

        //get extra string of intent
        //can be: showAllPictures or 14 names of alubms (me, family, friends etc..)
        //send to server this exta string request

        posts = findViewById(R.id.textView_posts);
        recyclerView =  findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        Intent intent = getIntent();
        String requestOfAllPictures = intent.getStringExtra("show_all_pictures");

        new GetPicturesFromServer(this, ConnectToServer.getDataOutputStream(),
                ConnectToServer.getObjectInputStream()).execute(requestOfAllPictures);


    }
}
