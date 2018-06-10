package com.tekapic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AlbumsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MyRecyclerViewAdapterAlbums adapter;
    TextView total;


    @Override
    public void onBackPressed() {

        new SendStringToServer(ConnectToServer.getDataOutputStream()).execute("albumNotSelected");

        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                new SendStringToServer(ConnectToServer.getDataOutputStream()).execute("albumNotSelected");
                break;
        }
        return false;
    }

    public void getPicturesByAlbum(View view) {

        Button button = (Button)view;
        String album = button.getText().toString();

        Intent intent = new Intent(this, PicturesActivity.class);
        intent.putExtra("show_all_pictures", album);
        startActivity(intent);

    }



    public void getAlbums(ArrayList<String> albums) {
        total.setText("total: " + Integer.toString(albums.size()));
        adapter = new MyRecyclerViewAdapterAlbums(albums, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_albums);


        total = findViewById(R.id.textView_total);
        recyclerView =  findViewById(R.id.recycler_view_albums);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        Intent intent = getIntent();
        String requestOfAlbums = intent.getStringExtra("show_albums");

        new GetAlbumsFromServer(this, ConnectToServer.getDataOutputStream(),
                ConnectToServer.getObjectInputStream()).execute(requestOfAlbums);


    }
}
