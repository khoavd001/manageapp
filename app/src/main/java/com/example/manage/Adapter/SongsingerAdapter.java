package com.example.manage.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.manage.Model.BaiHat;
import com.example.manage.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SongsingerAdapter extends RecyclerView.Adapter<SongsingerAdapter.ViewHolder> {
    Context context;
    ArrayList<BaiHat> arrayList;

    public SongsingerAdapter(Context context, ArrayList<BaiHat> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_songsinger, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BaiHat baiHat = arrayList.get(position);
        Picasso.with(context).load(baiHat.getHinhBaiHat()).into(holder.imageView);
        if(baiHat.getTenAllCaSi()=="")
        {
            holder.TenCaSi.setText("Khong co ca si");
        }else
            holder.TenCaSi.setText(baiHat.getTenAllCaSi());
        holder.TenBaiHat.setText(baiHat.getTenBaiHat());
        holder.idbaihat.setText(baiHat.getIdBaiHat());



    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView TenBaiHat, TenCaSi, idbaihat;
        ImageButton delete;
        ImageButton update;
        String tenbaihat,hinhbaihat,linkbaihat;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_songsinger);
            TenBaiHat = itemView.findViewById(R.id.txt_songname_songsinger);
            TenCaSi = itemView.findViewById(R.id.txt_singername_songsinger);
            delete=(ImageButton) itemView.findViewById(R.id.deletebtnsongsinger);
            idbaihat=itemView.findViewById(R.id.idbaihatsongsinger);

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    if (delete.){

                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });



        }
    }


    public void removedAt(int position){
        arrayList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, arrayList.size());
    }

    public void setArrayList(ArrayList<BaiHat> baiHats){
        arrayList = baiHats;
        notifyDataSetChanged();
    }

}
