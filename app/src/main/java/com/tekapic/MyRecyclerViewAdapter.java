package com.tekapic;

/**
 * Created by LEV on 02/06/2018.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {

    Context context;
    ArrayList<Picture> pictures = new ArrayList<Picture>();


    public MyRecyclerViewAdapter(ArrayList<Picture> list, Context context){
        this.pictures = list;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pictures_list, parent, false);
        MyViewHolder myViewHolder= new MyViewHolder(v);

        return myViewHolder;
    }




    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Bitmap imageBitmap = BitmapFactory.decodeByteArray(pictures.get(position).getPictureInByteArray()
                , 0,
                pictures.get(position).getPictureInByteArray().length);

        holder.imageView.setImageBitmap(imageBitmap);

    }

    @Override
    public int getItemCount() {
        return pictures.size();
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder
    {

        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.imageView);

        }
    }
}
