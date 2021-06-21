package com.example.manage.Adapter;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.manage.Activity.ui.song.UpdateSongActivity;
import com.example.manage.Model.BaiHat;
import com.example.manage.R;
import com.example.manage.Service.APIService;
import com.example.manage.Service.DataService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllSongAdapter extends RecyclerView.Adapter<AllSongAdapter.ViewHolder> {
    Context context;
    ArrayList<BaiHat> arrayList;

    public AllSongAdapter(Context context, ArrayList<BaiHat> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_all_song, parent, false);
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
        holder.tenbaihat=baiHat.getTenBaiHat().toString();
        holder.linkbaihat=baiHat.getLinkBaiHat().toString();
        holder.hinhbaihat=baiHat.getHinhBaiHat().toString();


    }

    @Override
    public int getItemCount() {
        if(arrayList!=null){
        return arrayList.size();}
        else
            return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView TenBaiHat, TenCaSi, idbaihat;
        ImageButton delete;
        ImageButton update;
        String tenbaihat,hinhbaihat,linkbaihat;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_song_all);
            TenBaiHat = itemView.findViewById(R.id.txt_song_all);
            TenCaSi = itemView.findViewById(R.id.txt_song_casi_all);
            delete=(ImageButton) itemView.findViewById(R.id.deletebtn);
            idbaihat=itemView.findViewById(R.id.idbaihat);

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id= Integer.parseInt(idbaihat.getText().toString());
                    deletebaihat(id);
                    removedAt(getAdapterPosition());
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, UpdateSongActivity.class);
                    intent.putExtra("tenbaihat",tenbaihat);
                    intent.putExtra("linkbaihat",linkbaihat);
                    intent.putExtra("hinhbaihat", hinhbaihat);
                    intent.putExtra("tencasi", TenCaSi.getText().toString());
                    intent.putExtra("idbaihat", idbaihat.getText().toString());
                    intent.putExtra("mangbaihat", arrayList);
                    intent.putExtra("position", getPosition());
                    context.startActivity(intent);
                }
            });



        }
    }

    private void deletebaihat(int idbaihat){
        DataService dataService= APIService.getService();
        Call<String> callback=dataService.GetDelteBaiHat(idbaihat);

        callback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String result=(String) response.body();

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

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