package com.example.manage.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.manage.Activity.ui.singer.DetailSinger;
import com.example.manage.Model.CaSi;
import com.example.manage.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AllSingerSongUpdate extends RecyclerView.Adapter<AllSingerSongUpdate.ViewHolder> {
    Context context;
    ArrayList<CaSi> arrayList;

    public AllSingerSongUpdate(Context context, ArrayList<CaSi> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_all_singer, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CaSi casi = arrayList.get(position);
        Picasso.with(context).load(casi.getHinhCaSi()).into(holder.imageView);
        holder.TenCaSi.setText(casi.getTenCaSi());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView imageView;
        TextView TenCaSi;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_singer_all);
            TenCaSi = itemView.findViewById(R.id.txt_singer_all);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ((Activity)context).finish();
                }
            });

        }
    }

}
