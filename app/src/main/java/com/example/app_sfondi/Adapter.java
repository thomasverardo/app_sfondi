package com.example.app_sfondi;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<com.example.app_sfondi.Adapter.VHolder>{

    ArrayList<Bitmap> data;
    Context context;

    public Adapter(ArrayList<Bitmap> data, Context context) {
        this.data = data;
        this.context = context;
    }

    public VHolder onCreateViewHolder(ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item, parent, false);
        return new VHolder(view);
    }

    public void onBindViewHolder(VHolder holder, int position){
        holder.imageButton.setImageBitmap(data.get(position));
    }

    public int getItemCount(){
        return data.size();
    }

    public class VHolder extends RecyclerView.ViewHolder{
        ImageButton imageButton;
        public VHolder(View itemView){
            super(itemView);
            imageButton = (ImageButton) itemView.findViewById(R.id.imageButton);
        }
    }
}
