package com.tekapic;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by LEV on 10/06/2018.
 */

public class MyRecyclerViewAdapterAlbums extends RecyclerView.Adapter<MyRecyclerViewAdapterAlbums.MyViewHolder> {

    Context context;
    ArrayList<String> albums = new ArrayList<String>();


    public MyRecyclerViewAdapterAlbums(ArrayList<String> list, Context context){
        this.albums = list;
        this.context = context;
    }

    @Override
    public MyRecyclerViewAdapterAlbums.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.albums_list, parent, false);
        MyRecyclerViewAdapterAlbums.MyViewHolder myViewHolder= new MyRecyclerViewAdapterAlbums.MyViewHolder(v);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyRecyclerViewAdapterAlbums.MyViewHolder holder, int position) {

        holder.button.setText(albums.get(position));

    }

    @Override
    public int getItemCount() {
        return albums.size();
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder
    {

        Button button;



        public MyViewHolder(View itemView) {
            super(itemView);

            button = (Button) itemView.findViewById(R.id.button_album);



        }
    }


}
